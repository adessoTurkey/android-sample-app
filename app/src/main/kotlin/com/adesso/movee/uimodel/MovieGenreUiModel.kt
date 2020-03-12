package com.adesso.movee.uimodel

import com.adesso.movee.base.ListAdapterItem

data class MovieGenreUiModel(
    override val id: Long,
    override val name: String
) : GenreUiModel, ListAdapterItem