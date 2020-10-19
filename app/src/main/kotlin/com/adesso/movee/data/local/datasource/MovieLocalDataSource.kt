package com.adesso.movee.data.local.datasource

import android.content.SharedPreferences
import com.adesso.movee.data.local.database.dao.MovieDao
import com.adesso.movee.data.local.database.dao.NowPlayingMovieIdDao
import com.adesso.movee.data.local.database.dao.PopularMovieIdDao
import com.adesso.movee.data.local.database.entity.MovieEntity
import com.adesso.movee.data.local.database.entity.NowPlayingMovieIdEntity
import com.adesso.movee.data.local.database.entity.PopularMovieIdEntity
import com.adesso.movee.data.local.delegate.listPreference
import com.adesso.movee.data.local.model.MovieGenreLocalModel
import com.squareup.moshi.Moshi
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    sharedPreferences: SharedPreferences,
    moshi: Moshi,
    private val movieDao: MovieDao,
    private val popularMovieIdDao: PopularMovieIdDao,
    private val nowPlayingMovieIdDao: NowPlayingMovieIdDao
) {

    private var genres: List<MovieGenreLocalModel>? by listPreference(
        moshi = moshi,
        preferenceName = PREF_GENRES,
        preferences = sharedPreferences
    )

    fun fetchGenres(): List<MovieGenreLocalModel>? {
        return genres
    }

    fun insertGenres(genreList: List<MovieGenreLocalModel>?) {
        genres = genreList
    }

    suspend fun getMoviesByIds(movieIds: List<Long>): List<MovieEntity> {
        return movieDao.getMoviesByIds(movieIds)
    }

    suspend fun getPopularMovieIds(): List<Long> {
        return popularMovieIdDao.getIds()
    }

    suspend fun getNowPlayingMovieIds(): List<Long> {
        return nowPlayingMovieIdDao.getIds()
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

    companion object {
        private const val PREF_GENRES = "movie_genres"
    }
}
