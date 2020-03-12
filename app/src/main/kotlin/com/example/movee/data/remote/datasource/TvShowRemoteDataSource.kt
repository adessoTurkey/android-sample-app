package com.example.movee.data.remote.datasource

import com.example.movee.data.remote.BaseRemoteDataSource
import com.example.movee.data.remote.api.TvShowService
import com.example.movee.data.remote.model.tv.NowPlayingTvShowResponseModel
import com.example.movee.data.remote.model.tv.TopRatedTvShowResponseModel
import com.example.movee.data.remote.model.tv.TvShowDetailResponseModel
import com.example.movee.data.remote.model.tv.TvShowGenreResponseModel
import javax.inject.Inject

class TvShowRemoteDataSource @Inject constructor(
    private val service: TvShowService
) : BaseRemoteDataSource() {

    suspend fun fetchTopRatedTvShows(): TopRatedTvShowResponseModel = invoke {
        service.fetchTopRatedTvShows()
    }

    suspend fun fetchTvShowGenres(): TvShowGenreResponseModel = invoke {
        service.fetchTvShowGenres()
    }

    suspend fun fetchNowPlayingTvShows(): NowPlayingTvShowResponseModel = invoke {
        service.fetchNowPlayingTvShows()
    }

    suspend fun fetchTvShowDetail(id: Long): TvShowDetailResponseModel = invoke {
        service.fetchTvShowDetail(id)
    }
}