package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowIdPageEntity
import com.adesso.movee.data.local.database.entity.TABLE_NOW_PLAYING_TV_SHOW_ID_PAGE

@Dao
abstract class NowPlayingTvShowIdPageDao : BaseDao<NowPlayingTvShowIdPageEntity> {

    @Query("SELECT page FROM $TABLE_NOW_PLAYING_TV_SHOW_ID_PAGE")
    abstract suspend fun getPages(): List<Int>

    @Query("DELETE FROM $TABLE_NOW_PLAYING_TV_SHOW_ID_PAGE")
    abstract suspend fun clear()
}
