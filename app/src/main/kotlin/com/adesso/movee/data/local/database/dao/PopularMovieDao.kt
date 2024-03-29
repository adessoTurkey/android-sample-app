package com.adesso.movee.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.adesso.movee.data.local.database.entity.PopularMovieEntity
import com.adesso.movee.data.local.database.entity.PopularMovieWithGenres

@Dao
abstract class PopularMovieDao : BaseDao<PopularMovieEntity> {

    @Transaction
    @Query("SELECT * FROM popular_movie WHERE id IN (:movieIds)")
    abstract suspend fun getMoviesWithGenresByIds(movieIds: List<Long>):
        List<PopularMovieWithGenres>

    @Transaction
    @Query("SELECT * FROM popular_movie")
    abstract fun getMoviesWithGenresPagingSource(): PagingSource<Int, PopularMovieWithGenres>

    @Transaction
    @Query("DELETE FROM popular_movie")
    abstract fun clearPopularMoviesWithGenres()
}
