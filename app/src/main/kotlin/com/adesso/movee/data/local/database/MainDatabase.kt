package com.adesso.movee.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adesso.movee.data.local.database.dao.MovieDao
import com.adesso.movee.data.local.database.dao.MovieGenreCrossRefDao
import com.adesso.movee.data.local.database.dao.MovieGenreDao
import com.adesso.movee.data.local.database.dao.NowPlayingMovieIdDao
import com.adesso.movee.data.local.database.dao.NowPlayingTvShowIdDao
import com.adesso.movee.data.local.database.dao.PopularMovieIdDao
import com.adesso.movee.data.local.database.dao.TopRatedTvShowIdDao
import com.adesso.movee.data.local.database.dao.TvShowDao
import com.adesso.movee.data.local.database.dao.TvShowGenreCrossRefDao
import com.adesso.movee.data.local.database.dao.TvShowGenreDao
import com.adesso.movee.data.local.database.entity.MovieEntity
import com.adesso.movee.data.local.database.entity.MovieGenreCrossRefEntity
import com.adesso.movee.data.local.database.entity.MovieGenreEntity
import com.adesso.movee.data.local.database.entity.NowPlayingMovieIdEntity
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowIdEntity
import com.adesso.movee.data.local.database.entity.PopularMovieIdEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowIdEntity
import com.adesso.movee.data.local.database.entity.TvShowEntity
import com.adesso.movee.data.local.database.entity.TvShowGenreCrossRefEntity
import com.adesso.movee.data.local.database.entity.TvShowGenreEntity
import com.adesso.movee.internal.util.typeconverter.DateTypeConverter
import com.adesso.movee.internal.util.typeconverter.GenreConverter

@Database(
    entities = [
        MovieEntity::class,
        PopularMovieIdEntity::class,
        NowPlayingMovieIdEntity::class,
        MovieGenreEntity::class,
        MovieGenreCrossRefEntity::class,
        TvShowEntity::class,
        TopRatedTvShowIdEntity::class,
        NowPlayingTvShowIdEntity::class,
        TvShowGenreEntity::class,
        TvShowGenreCrossRefEntity::class
    ],
    version = 2
)
@TypeConverters(
    DateTypeConverter::class,
    GenreConverter::class
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun popularMovieIdDao(): PopularMovieIdDao
    abstract fun nowPlayingMovieIdDao(): NowPlayingMovieIdDao
    abstract fun movieGenreDao(): MovieGenreDao
    abstract fun movieGenreCrossRefDao(): MovieGenreCrossRefDao

    abstract fun tvShowDao(): TvShowDao
    abstract fun topRatedTvShowIdDao(): TopRatedTvShowIdDao
    abstract fun nowPlayingTvShowIdDao(): NowPlayingTvShowIdDao
    abstract fun tvShowGenreDao(): TvShowGenreDao
    abstract fun tvShowGenreCrossRefDao(): TvShowGenreCrossRefDao
}
