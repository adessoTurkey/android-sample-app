package com.example.movee.data.remote.datasource

import com.example.movee.data.remote.BaseRemoteDataSource
import com.example.movee.data.remote.api.MovieService
import com.example.movee.data.remote.model.movie.MovieCreditsResponseModel
import com.example.movee.data.remote.model.movie.MovieDetailResponseModel
import com.example.movee.data.remote.model.movie.MovieGenreResponseModel
import com.example.movee.data.remote.model.movie.NowPlayingMovieResponseModel
import com.example.movee.data.remote.model.movie.PopularMovieResponseModel
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val service: MovieService
) : BaseRemoteDataSource() {

    suspend fun fetchPopularMovies(): PopularMovieResponseModel = invoke {
        service.fetchPopularMovies()
    }

    suspend fun fetchMovieGenres(): MovieGenreResponseModel = invoke {
        service.fetchMovieGenres()
    }

    suspend fun fetchNowPlayingMovies(): NowPlayingMovieResponseModel = invoke {
        service.fetchNowPlayingMovies()
    }

    suspend fun fetchMovieDetail(id: Long): MovieDetailResponseModel = invoke {
        service.fetchMovieDetail(id)
    }

    suspend fun fetchCredits(id: Long): MovieCreditsResponseModel = invoke {
        service.fetchCredits(id)
    }
}