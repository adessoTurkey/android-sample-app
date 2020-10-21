package com.adesso.movee.data.repository

import com.adesso.movee.data.local.database.entity.MovieGenreEntity
import com.adesso.movee.data.local.database.entity.NowPlayingMovieIdEntity
import com.adesso.movee.data.local.database.entity.PopularMovieIdEntity
import com.adesso.movee.data.local.datasource.MovieLocalDataSource
import com.adesso.movee.data.remote.datasource.MovieRemoteDataSource
import com.adesso.movee.uimodel.MovieUiModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@Singleton
class MovieRepository @Inject constructor(
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) {

    suspend fun fetchPopularMovies(): List<MovieUiModel> = coroutineScope {
        val deferredPopularMovieResponse = async { remoteDataSource.fetchPopularMovies() }
        checkMovieGenres()

        deferredPopularMovieResponse.await()
            .movieList
            .map { movieModel ->
                movieModel.genreIds.map {
                    localDataSource.insertMovieGenreCrossRef(
                        movieModel.toMovieGenreCrossRefEntity(it)
                    )
                }
                movieModel.toMovieEntity()
            }
            .also { movieEntityList ->
                localDataSource.insertMovies(movieEntityList)
                localDataSource.insertPopularMovieIds(
                    movieEntityList.map {
                        PopularMovieIdEntity(it.id)
                    }
                )
            }

        val movieIds = localDataSource.getPopularMovieIds()
        localDataSource
            .getMoviesWithGenres(movieIds)
            .map { movieWithGenres ->
                movieWithGenres.toUiModel()
            }
    }

    private suspend fun checkMovieGenres() {
        if (!doMovieGenresExist()) fetchMovieGenres()
    }

    private suspend fun doMovieGenresExist(): Boolean {
        return localDataSource.doMovieGenresExist()
    }

    private suspend fun fetchMovieGenres() {
        remoteDataSource
            .fetchMovieGenres()
            .genres
            .map { MovieGenreEntity(it.id, it.name) }
            .also { localDataSource.insertGenres(it) }
    }

    suspend fun fetchNowPlayingMovies(): List<MovieUiModel> = coroutineScope {
        val deferredNowPlayingMovieResponse = async { remoteDataSource.fetchNowPlayingMovies() }
        checkMovieGenres()

        deferredNowPlayingMovieResponse.await()
            .movieList
            .map { movieModel ->
                movieModel.genreIds.map {
                    localDataSource.insertMovieGenreCrossRef(
                        movieModel.toMovieGenreCrossRefEntity(it)
                    )
                }
                movieModel.toMovieEntity()
            }
            .also { movieEntityList ->
                localDataSource.insertMovies(movieEntityList)
                localDataSource.insertNowPlayingMovieIds(
                    movieEntityList.map {
                        NowPlayingMovieIdEntity(
                            it.id
                        )
                    }
                )
            }

        val movieIds = localDataSource.getNowPlayingMovieIds()
        localDataSource
            .getMoviesWithGenres(movieIds)
            .map { movieWithGenres ->
                movieWithGenres.toUiModel()
            }
    }

    suspend fun fetchMovieDetail(id: Long) = remoteDataSource.fetchMovieDetail(id).toUiModel()

    suspend fun fetchCredits(id: Long) = remoteDataSource.fetchCredits(id).toUiModel()
}
