package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.PopularMovieIdPageEntity
import com.adesso.movee.data.local.database.entity.TABLE_POPULAR_MOVIE_ID_PAGE

@Dao
abstract class PopularMovieIdPageDao : BaseDao<PopularMovieIdPageEntity> {

    @Query("SELECT page FROM $TABLE_POPULAR_MOVIE_ID_PAGE")
    abstract suspend fun getPages(): List<Int>

    @Query("DELETE FROM $TABLE_POPULAR_MOVIE_ID_PAGE")
    abstract suspend fun clear()
}
