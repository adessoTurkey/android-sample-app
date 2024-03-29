package com.adesso.movee.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.adesso.movee.data.local.database.entity.MovieGenreEntity
import com.adesso.movee.data.local.database.entity.NowPlayingMovieIdPageEntity
import com.adesso.movee.data.local.database.entity.PopularMovieEntity
import com.adesso.movee.data.local.database.entity.PopularMovieIdPageEntity
import com.adesso.movee.data.local.datasource.MovieLocalDataSource
import com.adesso.movee.data.remote.datasource.MovieRemoteDataSource
import com.adesso.movee.data.remote.mediator.PopularMovieRemoteMediator
import com.adesso.movee.data.remote.model.movie.PopularMovieResponseModel
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.uimodel.MovieUiModel
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.toResultOr
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@Singleton
class MovieRepository @Inject constructor(
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPopularMoviesPagingFlow(): Flow<PagingData<MovieUiModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PopularMovieRemoteMediator(
                fetchPopularMovies = ::fetchPopularMovies,
                getLastPageInLocal = localDataSource::getLastPageInDataSource
            ),
            pagingSourceFactory = { localDataSource.getPopularMoviesWithGenresPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toUiModel()
            }
        }
    }

    private suspend fun fetchPopularMovies(page: Int, clearLocalData: Boolean):
        Result<List<PopularMovieEntity>, Failure> {
            return try {
                coroutineScope {
                    val response: PopularMovieResponseModel

                    try {
                        val deferredPopularMovieResponse =
                            async { remoteDataSource.fetchPopularMovies(page) }
                        checkMovieGenres()

                        response = deferredPopularMovieResponse.await()
                    } catch (failure: Failure) {
                        throw failure
                    }

                    if (clearLocalData) {
                        withContext(Dispatchers.IO) { localDataSource.clearPopularMovieData() }
                    }

                    response
                        .movieList
                        .map { movieModel ->
                            movieModel.genreIds.map {
                                localDataSource.insertMovieGenreCrossRef(
                                    movieModel.toMovieGenreCrossRefEntity(it)
                                )
                            }
                            movieModel.toPopularMovieEntity()
                        }
                        .also { movieEntityList ->
                            localDataSource.insertPopularMovies(movieEntityList)
                            localDataSource.insertPopularMovieIds(
                                movieEntityList.map {
                                    PopularMovieIdPageEntity(it.id, page)
                                }
                            )
                        }.toResultOr { Failure.IoError }
                }
            } catch (failure: Failure) {
                Err(failure)
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
                movieModel.toNowPlayingMovieEntity()
            }
            .also { movieEntityList ->
                localDataSource.insertNowPlayingMovies(movieEntityList)
                localDataSource.insertNowPlayingMovieIds(
                    movieEntityList.map {
                        NowPlayingMovieIdPageEntity(
                            it.id, 1
                        )
                    }
                )
            }

        val movieIds = localDataSource.getNowPlayingMovieIds()
        localDataSource
            .getNowPlayingMoviesWithGenres(movieIds)
            .map { movieWithGenres ->
                movieWithGenres.toUiModel()
            }
    }

    suspend fun fetchMovieDetail(id: Long) = remoteDataSource.fetchMovieDetail(id).toUiModel()

    suspend fun fetchCredits(id: Long) = remoteDataSource.fetchCredits(id).toUiModel()
}
