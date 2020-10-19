package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_rated_tv_show_id")
data class TopRatedTvShowIdEntity(
    @ColumnInfo(name = "id") @PrimaryKey override val id: Long
) : BaseIdEntity
