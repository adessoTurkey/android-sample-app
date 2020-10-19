package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.TvShowEntity

@Dao
abstract class TvShowDao : BaseDao<TvShowEntity> {

    @Query("SELECT * FROM tv_show WHERE id IN (:tvShowIds)")
    abstract suspend fun getTvShowsByIds(tvShowIds: List<Long>): List<TvShowEntity>
}
