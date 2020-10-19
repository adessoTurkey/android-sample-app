package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.PopularMovieIdEntity

@Dao
abstract class PopularMovieIdDao : BaseDao<PopularMovieIdEntity> {

    @Query("SELECT id FROM popular_movie_id")
    abstract suspend fun getIds(): List<Long>
}
