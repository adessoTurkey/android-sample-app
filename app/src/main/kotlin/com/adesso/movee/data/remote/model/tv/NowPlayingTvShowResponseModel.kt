package com.adesso.movee.data.remote.model.tv

import com.squareup.moshi.Json

data class NowPlayingTvShowResponseModel(
    @Json(name = "results") val tvShowList: List<TvShowResponseModel>
)
