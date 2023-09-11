package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adesso.movee.internal.util.Image
import java.util.Date

@Entity(tableName = "now_playing_movie")
data class NowPlayingMovieEntity(
    @ColumnInfo(name = "id") val id: Long,
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
    @PrimaryKey(autoGenerate = true)
    var uId: Int = 0
}
