package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.TopRatedTvShowIdEntity

@Dao
abstract class TopRatedTvShowIdDao : BaseDao<TopRatedTvShowIdEntity> {

    @Query("SELECT id FROM top_rated_tv_show_id")
    abstract suspend fun getIds(): List<Long>
}
