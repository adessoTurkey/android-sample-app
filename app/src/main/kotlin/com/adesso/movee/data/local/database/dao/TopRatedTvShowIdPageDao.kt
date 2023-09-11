package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.TABLE_TOP_RATED_TV_SHOW_ID_PAGE
import com.adesso.movee.data.local.database.entity.TopRatedTvShowIdPageEntity

@Dao
abstract class TopRatedTvShowIdPageDao : BaseDao<TopRatedTvShowIdPageEntity> {
    @Query("SELECT page FROM $TABLE_TOP_RATED_TV_SHOW_ID_PAGE")
    abstract suspend fun getPages(): List<Int>

    @Query("DELETE FROM $TABLE_TOP_RATED_TV_SHOW_ID_PAGE")
    abstract suspend fun clear()
}
