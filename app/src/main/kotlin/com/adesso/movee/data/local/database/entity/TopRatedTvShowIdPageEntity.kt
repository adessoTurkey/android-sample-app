package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_TOP_RATED_TV_SHOW_ID_PAGE = "top_rated_tv_show_id_page"

@Entity(tableName = TABLE_TOP_RATED_TV_SHOW_ID_PAGE)
data class TopRatedTvShowIdPageEntity(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "page") val page: Int
) {
    @PrimaryKey(autoGenerate = true)
    var uId: Int = 0
}
