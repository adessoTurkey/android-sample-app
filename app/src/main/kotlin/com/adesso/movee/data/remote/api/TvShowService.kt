package com.adesso.movee.data.remote.api

import com.adesso.movee.data.remote.model.tv.NowPlayingTvShowResponseModel
import com.adesso.movee.data.remote.model.tv.TopRatedTvShowResponseModel
import com.adesso.movee.data.remote.model.tv.TvShowCreditsResponseModel
import com.adesso.movee.data.remote.model.tv.TvShowDetailResponseModel
import com.adesso.movee.data.remote.model.tv.TvShowGenreResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {

    @GET(TOP_RATED)
    suspend fun fetchTopRatedTvShows(@Query(QUERY_PAGE) page: Int = 1): TopRatedTvShowResponseModel

    @GET(GENRE)
    suspend fun fetchTvShowGenres(): TvShowGenreResponseModel

    @GET(ON_THE_AIR)
    suspend fun fetchNowPlayingTvShows(@Query(QUERY_PAGE) page: Int = 1):
        NowPlayingTvShowResponseModel

    @GET(DETAIL)
    suspend fun fetchTvShowDetail(@Path(PATH_TV_ID) id: Long): TvShowDetailResponseModel

    @GET(CREDIT)
    suspend fun fetchCredits(@Path(PATH_TV_ID) id: Long): TvShowCreditsResponseModel

    companion object {
        const val QUERY_PAGE = "page"
        const val TOP_RATED = "tv/top_rated"
        const val GENRE = "genre/tv/list"
        const val ON_THE_AIR = "tv/on_the_air"
        const val PATH_TV_ID = "tv_id"
        const val DETAIL = "tv/{$PATH_TV_ID}"
        const val CREDIT = "tv/{$PATH_TV_ID}/credits"
    }
}
