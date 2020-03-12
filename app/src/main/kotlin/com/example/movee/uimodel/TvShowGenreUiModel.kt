package com.example.movee.uimodel

import com.example.movee.base.ListAdapterItem

data class TvShowGenreUiModel(
    override val id: Long,
    override val name: String
) : GenreUiModel, ListAdapterItem