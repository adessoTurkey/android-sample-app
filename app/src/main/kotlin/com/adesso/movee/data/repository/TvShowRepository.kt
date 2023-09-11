package com.adesso.movee.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowEntity
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowIdPageEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowIdPageEntity
import com.adesso.movee.data.local.database.entity.TvShowGenreEntity
import com.adesso.movee.data.local.datasource.TvShowLocalDataSource
import com.adesso.movee.data.remote.datasource.TvShowRemoteDataSource
import com.adesso.movee.data.remote.mediator.GenericRemoteMediator
import com.adesso.movee.data.remote.model.tv.NowPlayingTvShowResponseModel
import com.adesso.movee.data.remote.model.tv.TopRatedTvShowResponseModel
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.uimodel.TvShowUiModel
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@Singleton
class TvShowRepository @Inject constructor(
    private val remoteDataSource: TvShowRemoteDataSource,
    private val localDataSource: TvShowLocalDataSource
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getTopRatedTvShowsPagingFlow(): Flow<PagingData<TvShowUiModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = GenericRemoteMediator(
                fetch = ::fetchTopRatedTvShows,
                getLastPageInLocal = localDataSource::getTopRatedTvShowsLastPage
            ),
            pagingSourceFactory = { localDataSource.getTopRatedTvShowsWithGenresPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toUiModel()
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getNowPlayingTvShowsPagingFlow(): Flow<PagingData<TvShowUiModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = GenericRemoteMediator(
                fetch = ::fetchNowPlayingTvShows,
                getLastPageInLocal = localDataSource::getNowPlayingTvShowsLastPage
            ),
            pagingSourceFactory = { localDataSource.getNowPlayingTvShowsWithGenresPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toUiModel()
            }
        }
    }

    private suspend fun fetchTopRatedTvShows(
        page: Int,
        clearLocalData: Boolean
    ): Result<List<TopRatedTvShowEntity>, Failure> {
        return try {
            coroutineScope {
                val response: TopRatedTvShowResponseModel

                try {
                    val deferredTopRatedTvShowResponse =
                        async { remoteDataSource.fetchTopRatedTvShows(page) }
                    checkTvShowGenres()

                    response = deferredTopRatedTvShowResponse.await()
                } catch (failure: Failure) {
                    throw failure
                }

                if (clearLocalData) {
                    withContext(Dispatchers.IO) { localDataSource.clearTopRatedTvShowsData() }
                }

                response.tvShowList
                    .map { tvShowModel ->
                        tvShowModel.genreIds.map {
                            localDataSource.insertTvShowGenreCrossRef(
                                tvShowModel.toTvShowGenreCrossRefEntity(it)
                            )
                        }
                        tvShowModel.toTopRatedEntity()
                    }
                    .also { tvShowEntityList ->
                        localDataSource.insertTopRatedTvShows(tvShowEntityList)
                        localDataSource.insertTopRatedTvShowIds(
                            tvShowEntityList.map {
                                TopRatedTvShowIdPageEntity(it.id, page)
                            }
                        )
                    }.run { Ok(this) }
            }
        } catch (failure: Failure) {
            Err(failure)
        }
    }

    private suspend fun checkTvShowGenres() {
        if (!doTvShowGenresExist()) fetchTvShowGenres()
    }

    private suspend fun doTvShowGenresExist(): Boolean {
        return localDataSource.doTvShowGenresExist()
    }

    private suspend fun fetchTvShowGenres() {
        remoteDataSource
            .fetchTvShowGenres()
            .genres
            .map { TvShowGenreEntity(it.id, it.name) }
            .also { localDataSource.insertGenres(it) }
    }

    private suspend fun fetchNowPlayingTvShows(
        page: Int,
        clearLocalData: Boolean
    ): Result<List<NowPlayingTvShowEntity>, Failure> {
        return try {
            coroutineScope {
                val response: NowPlayingTvShowResponseModel

                try {
                    val deferredNowPlayingTvShowResponse =
                        async { remoteDataSource.fetchNowPlayingTvShows(page) }
                    checkTvShowGenres()

                    response = deferredNowPlayingTvShowResponse.await()
                } catch (failure: Failure) {
                    throw failure
                }

                if (clearLocalData) {
                    withContext(Dispatchers.IO) { localDataSource.clearNowPlayingTvShowsData() }
                }

                response
                    .tvShowList
                    .map { tvShowModel ->
                        tvShowModel.genreIds.map {
                            localDataSource.insertTvShowGenreCrossRef(
                                tvShowModel.toTvShowGenreCrossRefEntity(it)
                            )
                        }
                        tvShowModel.toNowPlayingEntity()
                    }
                    .also { tvShowEntityList ->
                        localDataSource.insertNowPlayingTvShows(tvShowEntityList)
                        localDataSource.insertNowPlayingTvShowIds(
                            tvShowEntityList.map {
                                NowPlayingTvShowIdPageEntity(it.id, page)
                            }
                        )
                    }.run { Ok(this) }
            }
        } catch (failure: Failure) {
            Err(failure)
        }
    }

    suspend fun fetchTvShowDetail(id: Long) = remoteDataSource.fetchTvShowDetail(id).toUiModel()

    suspend fun fetchCredits(id: Long) = remoteDataSource.fetchCredits(id).toUiModel()
}
