package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NOW_PLAYING_MOVIE_ID_PAGE = "now_playing_movie_id_page"

@Entity(tableName = TABLE_NOW_PLAYING_MOVIE_ID_PAGE)
data class NowPlayingMovieIdPageEntity(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "page") val page: Int
) {
    @PrimaryKey(autoGenerate = true)
    var uId: Int = 0
}
