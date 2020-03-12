package com.example.movee.data.remote.model.tv

import com.example.movee.data.remote.BaseResponseModel
import com.squareup.moshi.Json

data class TopRatedTvShowResponseModel(
    @Json(name = "results") val tvShowList: List<TvShowResponseModel>
) : BaseResponseModel()
