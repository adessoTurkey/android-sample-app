package com.example.movee.data.local.model

import com.example.movee.data.local.BaseLocalModel
import com.example.movee.uimodel.MovieGenreUiModel

data class MovieGenreLocalModel(
    val id: Long,
    val name: String
) : BaseLocalModel() {

    fun toUiModel() = MovieGenreUiModel(id = id, name = name)
}