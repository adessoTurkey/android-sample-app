package com.adesso.movee.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowEntity
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowWithGenres

@Dao
abstract class NowPlayingTvShowDao : BaseDao<NowPlayingTvShowEntity> {

    @Transaction
    @Query("SELECT * FROM now_playing_tv_show")
    abstract fun getPagingSource(): PagingSource<Int, NowPlayingTvShowWithGenres>

    @Transaction
    @Query("DELETE FROM now_playing_tv_show")
    abstract suspend fun clear()
}
