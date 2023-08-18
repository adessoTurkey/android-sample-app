package com.adesso.movee.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adesso.movee.data.local.database.dao.MovieGenreCrossRefDao
import com.adesso.movee.data.local.database.dao.MovieGenreDao
import com.adesso.movee.data.local.database.dao.NowPlayingMovieDao
import com.adesso.movee.data.local.database.dao.NowPlayingMovieIdPageDao
import com.adesso.movee.data.local.database.dao.NowPlayingTvShowIdDao
import com.adesso.movee.data.local.database.dao.PopularMovieDao
import com.adesso.movee.data.local.database.dao.PopularMovieIdPageDao
import com.adesso.movee.data.local.database.dao.TopRatedTvShowIdDao
import com.adesso.movee.data.local.database.dao.TvShowDao
import com.adesso.movee.data.local.database.dao.TvShowGenreCrossRefDao
import com.adesso.movee.data.local.database.dao.TvShowGenreDao
import com.adesso.movee.data.local.database.entity.MovieGenreCrossRefEntity
import com.adesso.movee.data.local.database.entity.MovieGenreEntity
import com.adesso.movee.data.local.database.entity.NowPlayingMovieEntity
import com.adesso.movee.data.local.database.entity.NowPlayingMovieIdPageEntity
import com.adesso.movee.data.local.database.entity.NowPlayingTvShowIdEntity
import com.adesso.movee.data.local.database.entity.PopularMovieEntity
import com.adesso.movee.data.local.database.entity.PopularMovieIdPageEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowIdEntity
import com.adesso.movee.data.local.database.entity.TvShowEntity
import com.adesso.movee.data.local.database.entity.TvShowGenreCrossRefEntity
import com.adesso.movee.data.local.database.entity.TvShowGenreEntity
import com.adesso.movee.internal.util.typeconverter.DateTypeConverter
import com.adesso.movee.internal.util.typeconverter.GenreConverter

@Database(
    entities = [
        PopularMovieEntity::class,
        NowPlayingMovieEntity::class,
        PopularMovieIdPageEntity::class,
        NowPlayingMovieIdPageEntity::class,
        MovieGenreEntity::class,
        MovieGenreCrossRefEntity::class,
        TvShowEntity::class,
        TopRatedTvShowIdEntity::class,
        NowPlayingTvShowIdEntity::class,
        TvShowGenreEntity::class,
        TvShowGenreCrossRefEntity::class
    ],
    version = 3
)
@TypeConverters(
    DateTypeConverter::class,
    GenreConverter::class
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun popularMovieDao(): PopularMovieDao
    abstract fun nowPlayingMovieDao(): NowPlayingMovieDao
    abstract fun popularMovieIdDao(): PopularMovieIdPageDao
    abstract fun nowPlayingMovieIdDao(): NowPlayingMovieIdPageDao
    abstract fun movieGenreDao(): MovieGenreDao
    abstract fun movieGenreCrossRefDao(): MovieGenreCrossRefDao

    abstract fun tvShowDao(): TvShowDao
    abstract fun topRatedTvShowIdDao(): TopRatedTvShowIdDao
    abstract fun nowPlayingTvShowIdDao(): NowPlayingTvShowIdDao
    abstract fun tvShowGenreDao(): TvShowGenreDao
    abstract fun tvShowGenreCrossRefDao(): TvShowGenreCrossRefDao
}
