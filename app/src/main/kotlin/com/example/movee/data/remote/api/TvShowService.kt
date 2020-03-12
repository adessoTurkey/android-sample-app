package com.example.movee.data.remote.api

import com.example.movee.data.remote.model.tv.TvShowDetailResponseModel
import com.example.movee.data.remote.model.tv.NowPlayingTvShowResponseModel
import com.example.movee.data.remote.model.tv.TopRatedTvShowResponseModel
import com.example.movee.data.remote.model.tv.TvShowGenreResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface TvShowService {

    @GET(TOP_RATED)
    suspend fun fetchTopRatedTvShows(): TopRatedTvShowResponseModel

    @GET(GENRE)
    suspend fun fetchTvShowGenres(): TvShowGenreResponseModel

    @GET(ON_THE_AIR)
    suspend fun fetchNowPlayingTvShows(): NowPlayingTvShowResponseModel

    @GET(DETAIL)
    suspend fun fetchTvShowDetail(@Path(PATH_DETAIL_ID) id: Long): TvShowDetailResponseModel

    companion object {
        const val TOP_RATED = "tv/top_rated"
        const val GENRE = "genre/tv/list"
        const val ON_THE_AIR = "tv/on_the_air"
        const val PATH_DETAIL_ID = "tv_id"
        const val DETAIL = "tv/{$PATH_DETAIL_ID}"
    }
}