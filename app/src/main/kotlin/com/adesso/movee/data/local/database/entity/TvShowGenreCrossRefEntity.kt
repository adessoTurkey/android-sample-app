package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "tv_show_genre_cross_ref",
    primaryKeys = ["id", "genre_id"]
)
data class TvShowGenreCrossRefEntity(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "genre_id") val genreId: Long
)
