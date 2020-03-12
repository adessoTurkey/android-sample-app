package com.adesso.movee.uimodel

import com.adesso.movee.base.ListAdapterItem

data class TvShowGenreUiModel(
    override val id: Long,
    override val name: String
) : GenreUiModel, ListAdapterItem