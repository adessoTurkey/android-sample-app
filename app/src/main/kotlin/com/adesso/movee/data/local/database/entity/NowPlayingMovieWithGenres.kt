package com.adesso.movee.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.adesso.movee.internal.extension.convertMovieGenres
import com.adesso.movee.uimodel.MovieUiModel

class NowPlayingMovieWithGenres(
    @Embedded val movie: NowPlayingMovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genre_id",
        associateBy = Junction(
            value = MovieGenreCrossRefEntity::class
        )
    )
    val genres: List<MovieGenreEntity>
) {
    fun toUiModel() = MovieUiModel(
        id = movie.id,
        title = movie.title,
        overview = movie.overview,
        genres = genres.convertMovieGenres(),
        posterPath = movie.posterPath,
        backdropPath = movie.backdropPath,
        popularity = movie.popularity,
        average = movie.average,
        isAdult = movie.isAdult,
        releaseDate = movie.releaseDate
    )
}
