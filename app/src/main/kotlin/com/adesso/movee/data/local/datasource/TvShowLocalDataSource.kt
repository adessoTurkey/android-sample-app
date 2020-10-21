package com.adesso.movee.data.local.datasource

import com.adesso.movee.data.local.database.dao.NowPlayingTvShowIdDao
import com.adesso.movee.data.local.database.dao.TopRatedTvShowIdDao
import com.adesso.movee.data.local.database.dao.TvShowDao
import com.adesso.movee.data.local.database.dao.TvShowGenreCrossRefDao
import com.adesso.movee.data.local.database.dao.TvShowGenreDao
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowIdEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowIdEntity
import com.adesso.movee.data.local.database.entity.TvShowEntity
import com.adesso.movee.data.local.database.entity.TvShowGenreCrossRefEntity
import com.adesso.movee.data.local.database.entity.TvShowGenreEntity
import com.adesso.movee.data.local.database.entity.TvShowWithGenres
import javax.inject.Inject

class TvShowLocalDataSource @Inject constructor(
    private val tvShowDao: TvShowDao,
    private val topRatedTvShowIdDao: TopRatedTvShowIdDao,
    private val nowPlayingTvShowIdDao: NowPlayingTvShowIdDao,
    private val tvShowGenreDao: TvShowGenreDao,
    private val tvShowGenreCrossRefDao: TvShowGenreCrossRefDao
) {

    suspend fun doTvShowGenresExist(): Boolean {
        return tvShowGenreDao.doTvShowGenresExist()
    }

    suspend fun getTvShowsWithGenres(tvShowIds: List<Long>): List<TvShowWithGenres> {
        return tvShowDao.getTvShowsWithGenresByIds(tvShowIds)
    }

    suspend fun getTopRatedTvShowIds(): List<Long> {
        return topRatedTvShowIdDao.getIds()
    }

    suspend fun getNowPlayingTvShowIds(): List<Long> {
        return nowPlayingTvShowIdDao.getIds()
    }

    suspend fun insertGenres(genres: List<TvShowGenreEntity>) {
        tvShowGenreDao.insert(genres)
    }

    suspend fun insertTvShowGenreCrossRef(tvShowGenreCrossRefEntity: TvShowGenreCrossRefEntity) {
        tvShowGenreCrossRefDao.insert(tvShowGenreCrossRefEntity)
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
}
