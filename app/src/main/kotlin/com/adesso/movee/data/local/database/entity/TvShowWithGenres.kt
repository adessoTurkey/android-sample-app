package com.adesso.movee.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.adesso.movee.internal.extension.convertTvShowGenres
import com.adesso.movee.uimodel.TvShowUiModel

data class TvShowWithGenres(
    @Embedded val tvShow: TvShowEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genre_id",
        associateBy = Junction(
            value = TvShowGenreCrossRefEntity::class
        )
    )
    val genres: List<TvShowGenreEntity>
) {
    fun toUiModel() = TvShowUiModel(
        id = tvShow.id,
        title = tvShow.title,
        overview = tvShow.overview,
        genres = genres.convertTvShowGenres(),
        posterPath = tvShow.posterPath,
        backdropPath = tvShow.backdropPath,
        popularity = tvShow.popularity,
        average = tvShow.average,
        releaseDate = tvShow.releaseDate
    )
}
