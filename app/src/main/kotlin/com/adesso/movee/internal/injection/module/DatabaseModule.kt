package com.adesso.movee.internal.injection.module

import android.app.Application
import androidx.room.Room
import com.adesso.movee.data.local.database.MainDatabase
import com.adesso.movee.data.local.database.dao.MovieGenreCrossRefDao
import com.adesso.movee.data.local.database.dao.MovieGenreDao
import com.adesso.movee.data.local.database.dao.NowPlayingMovieDao
import com.adesso.movee.data.local.database.dao.NowPlayingMovieIdPageDao
import com.adesso.movee.data.local.database.dao.NowPlayingTvShowDao
import com.adesso.movee.data.local.database.dao.NowPlayingTvShowIdPageDao
import com.adesso.movee.data.local.database.dao.PopularMovieDao
import com.adesso.movee.data.local.database.dao.PopularMovieIdPageDao
import com.adesso.movee.data.local.database.dao.TopRatedTvShowDao
import com.adesso.movee.data.local.database.dao.TopRatedTvShowIdPageDao
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
    fun provideNowPlayingTvShowDao(mainDatabase: MainDatabase): NowPlayingTvShowDao {
        return mainDatabase.nowPlayingTvShowDao()
    }

    @Provides
    @Singleton
    fun provideTopRatedTvShowDao(mainDatabase: MainDatabase): TopRatedTvShowDao {
        return mainDatabase.topRatedTvShowDao()
    }

    @Provides
    @Singleton
    fun provideTopRatedTvShowIdPageDao(mainDatabase: MainDatabase): TopRatedTvShowIdPageDao {
        return mainDatabase.topRatedTvShowIdPageDao()
    }

    @Provides
    @Singleton
    fun provideNowPlayingTvShowIdPageDao(mainDatabase: MainDatabase): NowPlayingTvShowIdPageDao {
        return mainDatabase.nowPlayingTvShowIdPageDao()
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
