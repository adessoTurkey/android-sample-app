package com.adesso.movee.data.local.model

import com.adesso.movee.data.local.BaseLocalModel
import com.adesso.movee.uimodel.TvShowGenreUiModel

data class TvShowGenreLocalModel(
    val id: Long,
    val name: String
) : BaseLocalModel() {

    fun toUiModel() = TvShowGenreUiModel(id = id, name = name)
}