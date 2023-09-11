package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adesso.movee.internal.util.Image
import java.util.Date

@Entity(tableName = "now_playing_tv_show")
data class NowPlayingTvShowEntity(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "genre_ids") val genreIds: List<Long>,
    @ColumnInfo(name = "poster_path") @Image val posterPath: String?,
    @ColumnInfo(name = "backdrop_path") @Image val backdropPath: String?,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "vote_average") val average: Double,
    @ColumnInfo(name = "first_air_date") val releaseDate: Date?
) {
    @PrimaryKey(autoGenerate = true)
    var uId: Int = 0
}
