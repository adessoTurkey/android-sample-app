package com.example.movee.data.local.model

import com.example.movee.data.local.BaseLocalModel
import com.example.movee.uimodel.TvShowGenreUiModel

data class TvShowGenreLocalModel(
    val id: Long,
    val name: String
) : BaseLocalModel() {

    fun toUiModel() = TvShowGenreUiModel(id = id, name = name)
}