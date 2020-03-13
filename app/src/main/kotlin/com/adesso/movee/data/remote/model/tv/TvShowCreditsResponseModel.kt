package com.adesso.movee.data.remote.model.tv

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.uimodel.TvShowCreditUiModel
import com.squareup.moshi.Json

data class TvShowCreditsResponseModel(
    @Json(name = "cast") val cast: List<TvShowCastResponseModel>,
    @Json(name = "crew") val crew: List<TvShowCrewResponseModel>
) : BaseResponseModel() {

    fun toUiModel() = TvShowCreditUiModel(
        cast = cast.map { it.toUiModel() },
        crew = crew.map { it.toUiModel() }
    )
}