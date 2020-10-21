package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.MovieEntity

@Dao
abstract class MovieDao : BaseDao<MovieEntity> {

    @Query("SELECT * FROM movie WHERE id IN (:movieIds)")
    abstract suspend fun getMoviesByIds(movieIds: List<Long>): List<MovieEntity>
}
