package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.MovieGenreEntity

@Dao
abstract class MovieGenreDao : BaseDao<MovieGenreEntity> {

    @Query("SELECT COUNT(*) FROM movie_genre")
    abstract suspend fun getMovieGenreCount(): Int

    @Query("SELECT * FROM movie_genre")
    abstract suspend fun getMovieGenres(): List<MovieGenreEntity>

    @Query("SELECT EXISTS(SELECT * FROM movie_genre)")
    abstract suspend fun doMovieGenresExist(): Boolean
}
