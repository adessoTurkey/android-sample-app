package com.adesso.movee.internal.injection.module

import android.app.Application
import androidx.room.Room
import com.adesso.movee.data.local.database.MainDatabase
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
    fun provideMovieDao(mainDatabase: MainDatabase): MovieDao {
        return mainDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun providePopularMovieIdDao(mainDatabase: MainDatabase): PopularMovieIdDao {
        return mainDatabase.popularMovieIdDao()
    }

    @Provides
    @Singleton
    fun provideNowPlayingMovieIdDao(mainDatabase: MainDatabase): NowPlayingMovieIdDao {
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
