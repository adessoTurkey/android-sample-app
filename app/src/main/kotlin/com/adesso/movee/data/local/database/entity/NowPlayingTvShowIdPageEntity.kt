package com.adesso.movee.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NOW_PLAYING_TV_SHOW_ID_PAGE = "now_playing_tv_show_id_page"

@Entity(tableName = TABLE_NOW_PLAYING_TV_SHOW_ID_PAGE)
class NowPlayingTvShowIdPageEntity(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "page") val page: Int
) {
    @PrimaryKey(autoGenerate = true)
    var uId: Int = 0
}
