package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "now_playing_movie_id")
data class NowPlayingMovieIdEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long
)
