package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowIdEntity

@Dao
abstract class NowPlayingTvShowIdDao : BaseDao<NowPlayingTvShowIdEntity> {

    @Query("SELECT id FROM now_playing_tv_show_id")
    abstract suspend fun getIds(): List<Long>
}
