package com.adesso.movee.data.repository

import com.adesso.movee.data.local.datasource.TvShowLocalDataSource
import com.adesso.movee.data.remote.datasource.TvShowRemoteDataSource
import com.adesso.movee.uimodel.TvShowGenreUiModel
import com.adesso.movee.uimodel.TvShowUiModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

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
            .map { tvShowModel ->
                tvShowModel.toUiModel(filterGenreList(genres, tvShowModel.genreIds))
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
            .map { tvShowModel ->
                tvShowModel.toUiModel(filterGenreList(genres, tvShowModel.genreIds))
            }
    }

    suspend fun fetchTvShowDetail(id: Long) = remoteDataSource.fetchTvShowDetail(id).toUiModel()
}