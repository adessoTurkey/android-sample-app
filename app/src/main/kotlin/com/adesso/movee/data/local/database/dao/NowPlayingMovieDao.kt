package com.adesso.movee.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.adesso.movee.data.local.database.entity.NowPlayingMovieEntity
import com.adesso.movee.data.local.database.entity.NowPlayingMovieWithGenres

@Dao
abstract class NowPlayingMovieDao : BaseDao<NowPlayingMovieEntity> {

    @Transaction
    @Query("SELECT * FROM now_playing_movie WHERE id IN (:movieIds)")
    abstract suspend fun getMoviesWithGenresByIds(movieIds: List<Long>):
        List<NowPlayingMovieWithGenres>

    @Transaction
    @Query("SELECT * FROM now_playing_movie")
    abstract fun getMoviesWithGenresPagingSource(): PagingSource<Int, NowPlayingMovieWithGenres>

    @Transaction
    @Query("DELETE FROM now_playing_movie")
    abstract fun clearNowPlayingMoviesWithGenres()
}
