package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.adesso.movee.data.local.database.entity.TvShowEntity
import com.adesso.movee.data.local.database.entity.TvShowWithGenres

@Dao
abstract class TvShowDao : BaseDao<TvShowEntity> {

    @Transaction
    @Query("SELECT * FROM tv_show WHERE id IN (:tvShowIds)")
    abstract suspend fun getTvShowsWithGenresByIds(tvShowIds: List<Long>): List<TvShowWithGenres>
}
