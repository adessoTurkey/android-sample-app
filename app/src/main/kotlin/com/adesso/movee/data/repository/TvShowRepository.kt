package com.adesso.movee.data.repository

import com.adesso.movee.data.local.database.entity.NowPlayingTvShowIdEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowIdEntity
import com.adesso.movee.data.local.database.entity.TvShowGenreEntity
import com.adesso.movee.data.local.datasource.TvShowLocalDataSource
import com.adesso.movee.data.remote.datasource.TvShowRemoteDataSource
import com.adesso.movee.uimodel.TvShowUiModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@Singleton
class TvShowRepository @Inject constructor(
    private val remoteDataSource: TvShowRemoteDataSource,
    private val localDataSource: TvShowLocalDataSource
) {

    suspend fun fetchTopRatedTvShows(): List<TvShowUiModel> = coroutineScope {
        val deferredTopRatedTvShowResponse = async { remoteDataSource.fetchTopRatedTvShows() }
        checkTvShowGenres()

        deferredTopRatedTvShowResponse.await()
            .tvShowList
            .onEach { tvShowModel ->
                tvShowModel.genreIds.map {
                    localDataSource.insertTvShowGenreCrossRef(
                        tvShowModel.toTvShowGenreCrossRefEntity(it)
                    )
                }
            }
            .map { tvShowModel -> tvShowModel.toEntity() }
            .also { tvShowEntityList ->
                localDataSource.insertTvShows(tvShowEntityList)
                localDataSource.insertTopRatedTvShowIds(
                    tvShowEntityList.map {
                        TopRatedTvShowIdEntity(it.id)
                    }
                )
            }

        val tvShowIds = localDataSource.getTopRatedTvShowIds()
        localDataSource
            .getTvShowsWithGenres(tvShowIds)
            .map { tvShowWithGenres ->
                tvShowWithGenres.toUiModel()
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

    suspend fun fetchNowPlayingTvShows(): List<TvShowUiModel> = coroutineScope {
        val deferredNowPlayingTvShowResponse = async { remoteDataSource.fetchNowPlayingTvShows() }
        checkTvShowGenres()

        deferredNowPlayingTvShowResponse.await()
            .tvShowList
            .onEach { tvShowModel ->
                tvShowModel.genreIds.map {
                    localDataSource.insertTvShowGenreCrossRef(
                        tvShowModel.toTvShowGenreCrossRefEntity(it)
                    )
                }
            }
            .map { tvShowModel -> tvShowModel.toEntity() }
            .also { tvShowEntityList ->
                localDataSource.insertTvShows(tvShowEntityList)
                localDataSource.insertNowPlayingTvShowIds(
                    tvShowEntityList.map {
                        NowPlayingTvShowIdEntity(
                            it.id
                        )
                    }
                )
            }

        val tvShowIds = localDataSource.getNowPlayingTvShowIds()
        localDataSource
            .getTvShowsWithGenres(tvShowIds)
            .map { tvShowWithGenres ->
                tvShowWithGenres.toUiModel()
            }
    }

    suspend fun fetchTvShowDetail(id: Long) = remoteDataSource.fetchTvShowDetail(id).toUiModel()

    suspend fun fetchCredits(id: Long) = remoteDataSource.fetchCredits(id).toUiModel()
}
