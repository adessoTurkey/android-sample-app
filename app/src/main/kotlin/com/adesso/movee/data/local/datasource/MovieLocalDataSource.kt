package com.adesso.movee.data.local.datasource

import com.adesso.movee.data.local.database.dao.MovieDao
import com.adesso.movee.data.local.database.dao.MovieGenreCrossRefDao
import com.adesso.movee.data.local.database.dao.MovieGenreDao
import com.adesso.movee.data.local.database.dao.NowPlayingMovieIdDao
import com.adesso.movee.data.local.database.dao.PopularMovieIdDao
import com.adesso.movee.data.local.database.entity.MovieEntity
import com.adesso.movee.data.local.database.entity.MovieGenreCrossRefEntity
import com.adesso.movee.data.local.database.entity.MovieGenreEntity
import com.adesso.movee.data.local.database.entity.MovieWithGenres
import com.adesso.movee.data.local.database.entity.NowPlayingMovieIdEntity
import com.adesso.movee.data.local.database.entity.PopularMovieIdEntity
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val popularMovieIdDao: PopularMovieIdDao,
    private val nowPlayingMovieIdDao: NowPlayingMovieIdDao,
    private val movieGenreDao: MovieGenreDao,
    private val movieGenreCrossRefDao: MovieGenreCrossRefDao
) {

    suspend fun doMovieGenresExist(): Boolean {
        return movieGenreDao.doMovieGenresExist()
    }

    suspend fun getMoviesWithGenres(movieIds: List<Long>): List<MovieWithGenres> {
        return movieDao.getMoviesWithGenresByIds(movieIds)
    }

    suspend fun getPopularMovieIds(): List<Long> {
        return popularMovieIdDao.getIds()
    }

    suspend fun getNowPlayingMovieIds(): List<Long> {
        return nowPlayingMovieIdDao.getIds()
    }

    suspend fun insertGenres(genres: List<MovieGenreEntity>) {
        movieGenreDao.insert(genres)
    }

    suspend fun insertMovieGenreCrossRef(movieGenreCrossRefEntity: MovieGenreCrossRefEntity) {
        movieGenreCrossRefDao.insert(movieGenreCrossRefEntity)
    }

    suspend fun insertMovies(movieList: List<MovieEntity>) {
        movieDao.insert(movieList)
    }

    suspend fun insertPopularMovieIds(popularMovieIds: List<PopularMovieIdEntity>) {
        popularMovieIdDao.insert(popularMovieIds)
    }

    suspend fun insertNowPlayingMovieIds(nowPlayingMovieIds: List<NowPlayingMovieIdEntity>) {
        nowPlayingMovieIdDao.insert(nowPlayingMovieIds)
    }
}
