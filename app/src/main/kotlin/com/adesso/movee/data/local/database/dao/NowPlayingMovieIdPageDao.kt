package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.NowPlayingMovieIdPageEntity
import com.adesso.movee.data.local.database.entity.TABLE_NOW_PLAYING_MOVIE_ID_PAGE

@Dao
abstract class NowPlayingMovieIdPageDao : BaseDao<NowPlayingMovieIdPageEntity> {

    @Query("SELECT id FROM $TABLE_NOW_PLAYING_MOVIE_ID_PAGE")
    abstract suspend fun getIds(): List<Long>

    @Query("DELETE FROM $TABLE_NOW_PLAYING_MOVIE_ID_PAGE")
    abstract fun clearNowPlayingMovieIds()
}
