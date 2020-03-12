package com.adesso.movee.uimodel

import android.content.Context
import com.adesso.movee.R
import java.io.Serializable
import java.util.Date

data class TvShowDetailUiModel(
    override val id: Long,
    override val title: String,
    override val overview: String,
    override val genres: List<GenreUiModel>,
    override val posterPath: String?,
    override val average: Double,
    override val runtime: Int?,
    override val releaseDate: Date?,
    val seasonCount: Int,
    val popularity: Double,
    val backdropPath: String?,
    val creators: List<TvShowCreatorUiModel>
) : ShowDetailUiModel, Serializable{

    fun seasons(context: Context): String {
        return context.resources.getQuantityString(
            R.plurals.seasons,
            seasonCount,
            seasonCount
        )
    }

    val shouldShowCreators: Boolean get() = creators.isNotEmpty()
    val creatorsString: String get() = creators.joinToString(separator = ", ") { it.name }
}