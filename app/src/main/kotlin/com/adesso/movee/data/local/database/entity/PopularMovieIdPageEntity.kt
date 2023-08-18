package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_POPULAR_MOVIE_ID_PAGE = "popular_movie_id_page"

@Entity(tableName = TABLE_POPULAR_MOVIE_ID_PAGE)
data class PopularMovieIdPageEntity(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "page") val page: Int
) {
    @PrimaryKey(autoGenerate = true)
    var uId: Int = 0
}
