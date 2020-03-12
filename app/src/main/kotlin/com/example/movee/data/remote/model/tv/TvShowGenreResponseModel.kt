package com.example.movee.data.remote.model.tv

import com.example.movee.data.remote.BaseResponseModel
import com.squareup.moshi.Json

data class TvShowGenreResponseModel(
    @Json(name = "genres") val genres: List<TvShowGenreItemResponseModel>
) : BaseResponseModel()