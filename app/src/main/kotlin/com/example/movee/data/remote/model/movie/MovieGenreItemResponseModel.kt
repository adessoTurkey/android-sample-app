package com.example.movee.data.remote.model.movie

import com.example.movee.data.local.model.MovieGenreLocalModel
import com.example.movee.data.remote.BaseResponseModel
import com.example.movee.uimodel.MovieGenreUiModel
import com.squareup.moshi.Json

data class MovieGenreItemResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String
) : BaseResponseModel() {

    fun toUiModel() = MovieGenreUiModel(id = id, name = name)

    fun toCacheModel() = MovieGenreLocalModel(id = id, name = name)
}