package com.adesso.movee.data.local.datasource

import androidx.paging.PagingSource
import com.adesso.movee.data.local.database.dao.NowPlayingTvShowDao
import com.adesso.movee.data.local.database.dao.NowPlayingTvShowIdPageDao
import com.adesso.movee.data.local.database.dao.TopRatedTvShowDao
import com.adesso.movee.data.local.database.dao.TopRatedTvShowIdPageDao
import com.adesso.movee.data.local.database.dao.TvShowGenreCrossRefDao
import com.adesso.movee.data.local.database.dao.TvShowGenreDao
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowEntity
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowIdPageEntity
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowWithGenres
import com.adesso.movee.data.local.database.entity.TopRatedTvShowEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowIdPageEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowWithGenres
import com.adesso.movee.data.local.database.entity.TvShowGenreCrossRefEntity
import com.adesso.movee.data.local.database.entity.TvShowGenreEntity
import javax.inject.Inject

class TvShowLocalDataSource @Inject constructor(
    private val nowPlayingTvShowDao: NowPlayingTvShowDao,
    private val topRatedTvShowDao: TopRatedTvShowDao,
    private val topRatedTvShowIdPageDao: TopRatedTvShowIdPageDao,
    private val nowPlayingTvShowIdPageDao: NowPlayingTvShowIdPageDao,
    private val tvShowGenreDao: TvShowGenreDao,
    private val tvShowGenreCrossRefDao: TvShowGenreCrossRefDao
) {

    suspend fun clearNowPlayingTvShowsData() {
        nowPlayingTvShowDao.clear()
        nowPlayingTvShowIdPageDao.clear()
    }

    suspend fun clearTopRatedTvShowsData() {
        topRatedTvShowDao.clear()
        topRatedTvShowIdPageDao.clear()
    }

    suspend fun getTopRatedTvShowsLastPage(): Int? {
        return topRatedTvShowIdPageDao.getPages().lastOrNull()
    }

    suspend fun getNowPlayingTvShowsLastPage(): Int? {
        return nowPlayingTvShowIdPageDao.getPages().lastOrNull()
    }

    fun getTopRatedTvShowsWithGenresPagingSource():
        PagingSource<Int, TopRatedTvShowWithGenres> {
            return topRatedTvShowDao.getPagingSource()
        }

    fun getNowPlayingTvShowsWithGenresPagingSource():
        PagingSource<Int, NowPlayingTvShowWithGenres> {
            return nowPlayingTvShowDao.getPagingSource()
        }

    suspend fun doTvShowGenresExist(): Boolean {
        return tvShowGenreDao.doTvShowGenresExist()
    }

    suspend fun insertGenres(genres: List<TvShowGenreEntity>) {
        tvShowGenreDao.insert(genres)
    }

    suspend fun insertTvShowGenreCrossRef(tvShowGenreCrossRefEntity: TvShowGenreCrossRefEntity) {
        tvShowGenreCrossRefDao.insert(tvShowGenreCrossRefEntity)
    }

    suspend fun insertTopRatedTvShows(tvShowList: List<TopRatedTvShowEntity>) {
        topRatedTvShowDao.insert(tvShowList)
    }

    suspend fun insertNowPlayingTvShows(tvShowList: List<NowPlayingTvShowEntity>) {
        nowPlayingTvShowDao.insert(tvShowList)
    }

    suspend fun insertTopRatedTvShowIds(topRatedTvShowIds: List<TopRatedTvShowIdPageEntity>) {
        topRatedTvShowIdPageDao.insert(topRatedTvShowIds)
    }

    suspend fun insertNowPlayingTvShowIds(nowPlayingTvShowIds: List<NowPlayingTvShowIdPageEntity>) {
        nowPlayingTvShowIdPageDao.insert(nowPlayingTvShowIds)
    }
}
