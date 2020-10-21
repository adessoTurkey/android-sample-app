package com.adesso.movee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.adesso.movee.data.local.database.entity.TvShowGenreEntity

@Dao
abstract class TvShowGenreDao : BaseDao<TvShowGenreEntity> {

    @Query("SELECT EXISTS(SELECT * FROM tv_show_genre)")
    abstract suspend fun doTvShowGenresExist(): Boolean
}
