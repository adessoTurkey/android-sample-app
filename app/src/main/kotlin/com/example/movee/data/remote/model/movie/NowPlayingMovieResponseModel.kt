package com.example.movee.data.remote.model.movie

import com.example.movee.data.remote.BaseResponseModel
import com.squareup.moshi.Json

data class NowPlayingMovieResponseModel(
    @Json(name = "results") val movieList: List<MovieResponseModel>
) : BaseResponseModel()
