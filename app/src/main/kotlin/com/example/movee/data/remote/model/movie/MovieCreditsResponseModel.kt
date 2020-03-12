package com.example.movee.data.remote.model.movie

import com.example.movee.data.remote.BaseResponseModel
import com.example.movee.uimodel.MovieCreditUiModel
import com.squareup.moshi.Json

data class MovieCreditsResponseModel(
    @Json(name = "cast") val cast: List<MovieCastResponseModel>,
    @Json(name = "crew") val crew: List<MovieCrewResponseModel>
) : BaseResponseModel() {

    fun toUiModel() = MovieCreditUiModel(
        cast = cast.map { it.toUiModel() },
        crew = crew.map { it.toUiModel() }
    )
}