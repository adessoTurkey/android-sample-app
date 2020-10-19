package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movie_id")
data class PopularMovieIdEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long
)
