package com.example.movee.data.remote.model.movie

import com.example.movee.data.remote.BaseResponseModel
import com.squareup.moshi.Json

data class MovieGenreResponseModel(
    @Json(name = "genres") val genres: List<MovieGenreItemResponseModel>
) : BaseResponseModel()