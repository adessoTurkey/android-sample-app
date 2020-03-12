package com.example.movee.uimodel

import com.example.movee.base.ListAdapterItem
import com.example.movee.internal.extension.formatDate
import com.example.movee.internal.util.Constant
import java.io.Serializable
import java.util.Date

interface ShowUiModel : ListAdapterItem, Serializable {
    override val id: Long

    val title: String

    val overview: String

    val genres: List<GenreUiModel>

    val posterPath: String?

    val average: Double

    val releaseDate: Date?

    val averageString: String get() = average.toString()

    val releaseDateString: String
        get() = releaseDate?.formatDate(Constant.Date.DATE_FORMAT_SHOW) ?: ""

    val genreString: String
        get() = genres.joinToString(separator = ", ", limit = 3, truncated = "") { it.name }

}