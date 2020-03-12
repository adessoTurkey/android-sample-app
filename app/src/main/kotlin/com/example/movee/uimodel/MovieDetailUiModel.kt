package com.example.movee.uimodel

import java.io.Serializable
import java.util.Date

data class MovieDetailUiModel(
    override val id: Long,
    override val title: String,
    override val overview: String,
    override val genres: List<GenreUiModel>,
    override val posterPath: String?,
    override val average: Double,
    override val runtime: Int?,
    override val releaseDate: Date?,
    val popularity: Double
) : ShowDetailUiModel, Serializable