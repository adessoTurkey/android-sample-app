package com.adesso.movee.data.local.datasource

import androidx.paging.PagingSource
import com.adesso.movee.data.local.database.dao.MovieGenreCrossRefDao
import com.adesso.movee.data.local.database.dao.MovieGenreDao
import com.adesso.movee.data.local.database.dao.NowPlayingMovieDao
import com.adesso.movee.data.local.database.dao.NowPlayingMovieIdPageDao
import com.adesso.movee.data.local.database.dao.PopularMovieDao
import com.adesso.movee.data.local.database.dao.PopularMovieIdPageDao
import com.adesso.movee.data.local.database.entity.MovieGenreCrossRefEntity
import com.adesso.movee.data.local.database.entity.MovieGenreEntity
import com.adesso.movee.data.local.database.entity.NowPlayingMovieEntity
import com.adesso.movee.data.local.database.entity.NowPlayingMovieIdPageEntity
import com.adesso.movee.data.local.database.entity.NowPlayingMovieWithGenres
import com.adesso.movee.data.local.database.entity.PopularMovieEntity
import com.adesso.movee.data.local.database.entity.PopularMovieIdPageEntity
import com.adesso.movee.data.local.database.entity.PopularMovieWithGenres
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val popularMovieDao: PopularMovieDao,
    private val nowPlayingMovieDao: NowPlayingMovieDao,
    private val popularMovieIdPageDao: PopularMovieIdPageDao,
    private val nowPlayingMovieIdPageDao: NowPlayingMovieIdPageDao,
    private val movieGenreDao: MovieGenreDao,
    private val movieGenreCrossRefDao: MovieGenreCrossRefDao
) {

    suspend fun doMovieGenresExist(): Boolean {
        return movieGenreDao.doMovieGenresExist()
    }

    fun getPopularMoviesWithGenresPagingSource(): PagingSource<Int, PopularMovieWithGenres> {
        return popularMovieDao.getPagingSource()
    }

    fun getNowPlayingMoviesWithGenresPagingSource(): PagingSource<Int, NowPlayingMovieWithGenres> {
        return nowPlayingMovieDao.getPagingSource()
    }

    suspend fun clearPopularMovieData() {
        popularMovieDao.clear()
        popularMovieIdPageDao.clear()
    }

    suspend fun clearNowPlayingMovieData() { // Will be used for now playing movies paging.
        nowPlayingMovieDao.clear()
        nowPlayingMovieIdPageDao.clear()
    }

    suspend fun getPopularMoviesLastPage(): Int? {
        return popularMovieIdPageDao.getPages().lastOrNull()
    }

    suspend fun getMovieNowPlayingLastPage(): Int? {
        return nowPlayingMovieIdPageDao.getPages().lastOrNull()
    }

    suspend fun insertGenres(genres: List<MovieGenreEntity>) {
        movieGenreDao.insert(genres)
    }

    suspend fun insertMovieGenreCrossRef(movieGenreCrossRefEntity: MovieGenreCrossRefEntity) {
        movieGenreCrossRefDao.insert(movieGenreCrossRefEntity)
    }

    suspend fun insertPopularMovies(movieList: List<PopularMovieEntity>) {
        popularMovieDao.insert(movieList)
    }

    suspend fun insertNowPlayingMovies(movieList: List<NowPlayingMovieEntity>) {
        nowPlayingMovieDao.insert(movieList)
    }

    suspend fun insertPopularMovieIds(popularMovieIds: List<PopularMovieIdPageEntity>) {
        popularMovieIdPageDao.insert(popularMovieIds)
    }

    suspend fun insertNowPlayingMovieIds(nowPlayingMovieIds: List<NowPlayingMovieIdPageEntity>) {
        nowPlayingMovieIdPageDao.insert(nowPlayingMovieIds)
    }
}
