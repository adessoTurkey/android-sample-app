package com.adesso.movee.internal.injection.module

import android.app.Application
import androidx.room.Room
import com.adesso.movee.data.local.database.MainDatabase
import com.adesso.movee.data.local.database.dao.PopularMovieDao
import com.adesso.movee.data.local.database.dao.MovieGenreCrossRefDao
import com.adesso.movee.data.local.database.dao.MovieGenreDao
import com.adesso.movee.data.local.database.dao.NowPlayingMovieDao
import com.adesso.movee.data.local.database.dao.NowPlayingMovieIdPageDao
import com.adesso.movee.data.local.database.dao.NowPlayingTvShowIdDao
import com.adesso.movee.data.local.database.dao.PopularMovieIdPageDao
import com.adesso.movee.data.local.database.dao.TopRatedTvShowIdDao
import com.adesso.movee.data.local.database.dao.TvShowDao
import com.adesso.movee.data.local.database.dao.TvShowGenreCrossRefDao
import com.adesso.movee.data.local.database.dao.TvShowGenreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "movee.db"

    @Provides
    @Singleton
    fun provideMainDatabase(application: Application): MainDatabase {
        return Room.databaseBuilder(
            application.applicationContext, MainDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePopularMovieDao(mainDatabase: MainDatabase): PopularMovieDao {
        return mainDatabase.popularMovieDao()
    }

    @Provides
    @Singleton
    fun provideNowPlayingMovieDao(mainDatabase: MainDatabase): NowPlayingMovieDao {
        return mainDatabase.nowPlayingMovieDao()
    }

    @Provides
    @Singleton
    internal fun providePopularMovieIdDao(mainDatabase: MainDatabase): PopularMovieIdPageDao {
        return mainDatabase.popularMovieIdDao()
    }

    @Provides
    @Singleton
    internal fun provideNowPlayingMovieIdDao(mainDatabase: MainDatabase): NowPlayingMovieIdPageDao {
        return mainDatabase.nowPlayingMovieIdDao()
    }

    @Provides
    @Singleton
    fun provideMovieGenreDao(mainDatabase: MainDatabase): MovieGenreDao {
        return mainDatabase.movieGenreDao()
    }

    @Provides
    @Singleton
    fun provideMovieGenreCrossRefDao(mainDatabase: MainDatabase): MovieGenreCrossRefDao {
        return mainDatabase.movieGenreCrossRefDao()
    }

    @Provides
    @Singleton
    fun provideTvShowDao(mainDatabase: MainDatabase): TvShowDao {
        return mainDatabase.tvShowDao()
    }

    @Provides
    @Singleton
    fun provideTopRatedTvShowIdDao(mainDatabase: MainDatabase): TopRatedTvShowIdDao {
        return mainDatabase.topRatedTvShowIdDao()
    }

    @Provides
    @Singleton
    fun provideNowPlayingTvShowIdDao(mainDatabase: MainDatabase): NowPlayingTvShowIdDao {
        return mainDatabase.nowPlayingTvShowIdDao()
    }

    @Provides
    @Singleton
    fun provideTvShowGenreDao(mainDatabase: MainDatabase): TvShowGenreDao {
        return mainDatabase.tvShowGenreDao()
    }

    @Provides
    @Singleton
    fun provideTvShowGenreCrossRefDao(mainDatabase: MainDatabase): TvShowGenreCrossRefDao {
        return mainDatabase.tvShowGenreCrossRefDao()
    }
}
