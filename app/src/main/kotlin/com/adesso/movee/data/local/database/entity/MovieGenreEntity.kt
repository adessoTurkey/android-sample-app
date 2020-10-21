package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_genre")
data class MovieGenreEntity(
    @ColumnInfo(name = "genre_id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String
)
