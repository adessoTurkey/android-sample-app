package com.adesso.movee.uimodel

import com.adesso.movee.base.ListAdapterItem
import java.util.Date

data class TvShowUiModel(
    override val id: Long,
    override val title: String,
    override val overview: String,
    override val genres: List<TvShowGenreUiModel>,
    override val posterPath: String?,
    override val average: Double,
    override val releaseDate: Date?,
    val popularity: Double,
    val backdropPath: String?
) : ShowUiModel, ListAdapterItem