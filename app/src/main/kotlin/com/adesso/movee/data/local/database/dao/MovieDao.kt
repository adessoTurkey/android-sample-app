package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.adesso.movee.data.local.database.entity.MovieEntity
import com.adesso.movee.data.local.database.entity.MovieWithGenres

@Dao
abstract class MovieDao : BaseDao<MovieEntity> {

    @Transaction
    @Query("SELECT * FROM movie WHERE id IN (:movieIds)")
    abstract suspend fun getMoviesWithGenresByIds(movieIds: List<Long>): List<MovieWithGenres>
}
