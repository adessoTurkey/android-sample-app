package com.adesso.movee.data.repository

import com.adesso.movee.data.local.database.entity.NowPlayingTvShowIdEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowIdEntity
import com.adesso.movee.data.local.datasource.TvShowLocalDataSource
import com.adesso.movee.data.remote.datasource.TvShowRemoteDataSource
import com.adesso.movee.uimodel.TvShowGenreUiModel
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
        val genres = fetchTvShowGenres()

        deferredTopRatedTvShowResponse.await()
            .tvShowList
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
            .getTvShowsByIds(tvShowIds)
            .map { tvShowEntity ->
                tvShowEntity.toUiModel(filterGenreList(genres, tvShowEntity.genreIds))
            }
    }

    private fun filterGenreList(
        genreList: List<TvShowGenreUiModel>,
        genreIds: List<Long>
    ): List<TvShowGenreUiModel> {
        val genres = mutableListOf<TvShowGenreUiModel>()

        genreIds.forEach { genreId ->

            genreList.firstOrNull { genre ->
                genre.id == genreId
            }?.let { genres.add(it) }
        }
        return genres
    }

    suspend fun fetchTvShowGenres(): List<TvShowGenreUiModel> {
        var localGenres = localDataSource.fetchGenres()

        if (localGenres.isNullOrEmpty()) {
            localGenres = remoteDataSource.fetchTvShowGenres()
                .genres
                .map { it.toCacheModel() }
                .also { localDataSource.insertGenres(it) }
        }

        return localGenres.map { it.toUiModel() }
    }

    suspend fun fetchNowPlayingTvShows(): List<TvShowUiModel> = coroutineScope {
        val deferredNowPlayingTvShowResponse = async { remoteDataSource.fetchNowPlayingTvShows() }
        val genres = fetchTvShowGenres()

        deferredNowPlayingTvShowResponse.await()
            .tvShowList
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
            .getTvShowsByIds(tvShowIds)
            .map { tvShowEntity ->
                tvShowEntity.toUiModel(filterGenreList(genres, tvShowEntity.genreIds))
            }
    }

    suspend fun fetchTvShowDetail(id: Long) = remoteDataSource.fetchTvShowDetail(id).toUiModel()

    suspend fun fetchCredits(id: Long) = remoteDataSource.fetchCredits(id).toUiModel()
}
