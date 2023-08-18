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
        return popularMovieDao.getMoviesWithGenresPagingSource()
    }

    fun clearPopularMovieData() {
        popularMovieDao.clearPopularMoviesWithGenres()
        popularMovieIdPageDao.clearPopularMovieIds()
    }

    fun clearNowPlayingMovieData() { // Will be used for now playing movies paging.
        nowPlayingMovieDao.clearNowPlayingMoviesWithGenres()
        nowPlayingMovieIdPageDao.clearNowPlayingMovieIds()
    }

    suspend fun getLastPageInDataSource(): Int? {
        return popularMovieIdPageDao.getPopularMoviePages().lastOrNull()
    }

    suspend fun getPopularMoviesWithGenres(movieIds: List<Long>): List<PopularMovieWithGenres> {
        return popularMovieDao.getMoviesWithGenresByIds(movieIds)
    }

    suspend fun getNowPlayingMoviesWithGenres(movieIds: List<Long>):
        List<NowPlayingMovieWithGenres> {
            return nowPlayingMovieDao.getMoviesWithGenresByIds(movieIds)
        }

    suspend fun getPopularMovieIds(): List<Long> {
        return popularMovieIdPageDao.getIds()
    }

    suspend fun getNowPlayingMovieIds(): List<Long> {
        return nowPlayingMovieIdPageDao.getIds()
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
