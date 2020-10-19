package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.MovieGenreUiModel
import com.adesso.movee.uimodel.MovieUiModel
import java.util.Date

@Entity(tableName = "movie")
data class MovieEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "genres") val genreIds: List<Long>,
    @ColumnInfo(name = "poster_path") @Image val posterPath: String?,
    @ColumnInfo(name = "backdrop_path") @Image val backdropPath: String?,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "vote_average") val average: Double,
    @ColumnInfo(name = "adult") val isAdult: Boolean,
    @ColumnInfo(name = "release_date") val releaseDate: Date?
) {

    fun toUiModel(genres: List<MovieGenreUiModel>) = MovieUiModel(
        id = id,
        title = title,
        overview = overview,
        genres = genres,
        posterPath = posterPath,
        backdropPath = backdropPath,
        popularity = popularity,
        average = average,
        isAdult = isAdult,
        releaseDate = releaseDate
    )
}
