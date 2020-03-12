package com.example.movee.data.repository

import com.example.movee.data.local.datasource.MovieLocalDataSource
import com.example.movee.data.remote.datasource.MovieRemoteDataSource
import com.example.movee.uimodel.MovieGenreUiModel
import com.example.movee.uimodel.MovieUiModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) {

    suspend fun fetchPopularMovies(): List<MovieUiModel> = coroutineScope {
        val deferredPopularMovieResponse = async { remoteDataSource.fetchPopularMovies() }
        val genres = fetchMovieGenres()

        deferredPopularMovieResponse.await()
            .movieList
            .map { movieModel ->
                movieModel.toUiModel(filterGenreList(genres, movieModel.genreIds))
            }
    }

    private fun filterGenreList(
        genreList: List<MovieGenreUiModel>,
        genreIds: List<Long>
    ): List<MovieGenreUiModel> {
        return genreIds.map { genreId ->
            genreList.first { genre ->
                genre.id == genreId
            }
        }
    }

    suspend fun fetchMovieGenres(): List<MovieGenreUiModel> {
        var localGenres = localDataSource.fetchGenres()

        if (localGenres.isNullOrEmpty()) {
            localGenres = remoteDataSource.fetchMovieGenres()
                .genres
                .map { it.toCacheModel() }
                .also { localDataSource.insertGenres(it) }
        }

        return localGenres.map { it.toUiModel() }
    }

    suspend fun fetchNowPlayingMovies(): List<MovieUiModel> = coroutineScope {
        val deferredPopularMovieResponse = async { remoteDataSource.fetchNowPlayingMovies() }
        val genres = fetchMovieGenres()

        deferredPopularMovieResponse.await()
            .movieList
            .map { movieModel ->
                movieModel.toUiModel(filterGenreList(genres, movieModel.genreIds))
            }
    }

    suspend fun fetchMovieDetail(id: Long) = remoteDataSource.fetchMovieDetail(id).toUiModel()

    suspend fun fetchCredits(id: Long) = remoteDataSource.fetchCredits(id).toUiModel()
}