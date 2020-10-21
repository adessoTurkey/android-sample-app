package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.NowPlayingMovieIdEntity

@Dao
abstract class NowPlayingMovieIdDao : BaseDao<NowPlayingMovieIdEntity> {

    @Query("SELECT id FROM now_playing_movie_id")
    abstract suspend fun getIds(): List<Long>
}
