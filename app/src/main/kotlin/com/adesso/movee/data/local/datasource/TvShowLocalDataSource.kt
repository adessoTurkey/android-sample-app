package com.adesso.movee.data.local.datasource

import android.content.SharedPreferences
import com.adesso.movee.data.local.database.dao.NowPlayingTvShowIdDao
import com.adesso.movee.data.local.database.dao.TopRatedTvShowIdDao
import com.adesso.movee.data.local.database.dao.TvShowDao
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowIdEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowIdEntity
import com.adesso.movee.data.local.database.entity.TvShowEntity
import com.adesso.movee.data.local.delegate.listPreference
import com.adesso.movee.data.local.model.TvShowGenreLocalModel
import com.squareup.moshi.Moshi
import javax.inject.Inject

class TvShowLocalDataSource @Inject constructor(
    sharedPreferences: SharedPreferences,
    moshi: Moshi,
    private val tvShowDao: TvShowDao,
    private val topRatedTvShowIdDao: TopRatedTvShowIdDao,
    private val nowPlayingTvShowIdDao: NowPlayingTvShowIdDao
) {

    private var genres: List<TvShowGenreLocalModel>? by listPreference(
        moshi = moshi,
        preferences = sharedPreferences,
        preferenceName = PREF_GENRES
    )

    fun fetchGenres(): List<TvShowGenreLocalModel>? = genres

    fun insertGenres(genreList: List<TvShowGenreLocalModel>?) {
        genres = genreList
    }

    suspend fun getTvShowsByIds(tvShowIds: List<Long>): List<TvShowEntity> {
        return tvShowDao.getTvShowsByIds(tvShowIds)
    }

    suspend fun getTopRatedTvShowIds(): List<Long> {
        return topRatedTvShowIdDao.getIds()
    }

    suspend fun getNowPlayingTvShowIds(): List<Long> {
        return nowPlayingTvShowIdDao.getIds()
    }

    suspend fun insertTvShows(tvShowList: List<TvShowEntity>) {
        tvShowDao.insert(tvShowList)
    }

    suspend fun insertTopRatedTvShowIds(topRatedTvShowIds: List<TopRatedTvShowIdEntity>) {
        topRatedTvShowIdDao.insert(topRatedTvShowIds)
    }

    suspend fun insertNowPlayingTvShowIds(nowPlayingTvShowIds: List<NowPlayingTvShowIdEntity>) {
        nowPlayingTvShowIdDao.insert(nowPlayingTvShowIds)
    }

    companion object {
        private const val PREF_GENRES = "tv_show_genres"
    }
}
