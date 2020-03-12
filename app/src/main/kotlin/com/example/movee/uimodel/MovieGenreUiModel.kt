package com.example.movee.uimodel

import com.example.movee.base.ListAdapterItem

data class MovieGenreUiModel(
    override val id: Long,
    override val name: String
) : GenreUiModel, ListAdapterItem