package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_genre")
data class TvShowGenreEntity(
    @ColumnInfo(name = "genre_id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String
)
