package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "now_playing_tv_show_id")
data class NowPlayingTvShowIdEntity(
    @ColumnInfo(name = "id") @PrimaryKey override val id: Long
) : BaseIdEntity
