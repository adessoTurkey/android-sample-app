package com.adesso.movee.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.adesso.movee.data.local.database.entity.TopRatedTvShowEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowWithGenres

@Dao
abstract class TopRatedTvShowDao : BaseDao<TopRatedTvShowEntity> {

    @Transaction
    @Query("SELECT * FROM top_rated_tv_show")
    abstract fun getPagingSource(): PagingSource<Int, TopRatedTvShowWithGenres>

    @Transaction
    @Query("DELETE FROM top_rated_tv_show")
    abstract suspend fun clear()
}
