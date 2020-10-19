package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.TvShowGenreUiModel
import com.adesso.movee.uimodel.TvShowUiModel
import java.util.Date

@Entity(tableName = "tv_show")
data class TvShowEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "genre_ids") val genreIds: List<Long>,
    @ColumnInfo(name = "poster_path") @Image val posterPath: String?,
    @ColumnInfo(name = "backdrop_path") @Image val backdropPath: String?,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "vote_average") val average: Double,
    @ColumnInfo(name = "first_air_date") val releaseDate: Date?
) {
    fun toUiModel(genres: List<TvShowGenreUiModel>) = TvShowUiModel(
        id = id,
        title = title,
        overview = overview,
        genres = genres,
        posterPath = posterPath,
        backdropPath = backdropPath,
        popularity = popularity,
        average = average,
        releaseDate = releaseDate
    )
}
