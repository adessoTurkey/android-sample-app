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
import javax.inject.Singleton

@Module
internal class DatabaseModule {

    companion object {
        private const val DATABASE_NAME = "movee.db"
    }

    @Provides
    @Singleton
    internal fun provideMainDatabase(application: Application): MainDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            MainDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    internal fun provideMovieDao(mainDatabase: MainDatabase): MovieDao {
        return mainDatabase.movieDao()
    }

    @Provides
    @Singleton
    internal fun providePopularMovieIdDao(mainDatabase: MainDatabase): PopularMovieIdDao {
        return mainDatabase.popularMovieIdDao()
    }

    @Provides
    @Singleton
    internal fun provideNowPlayingMovieIdDao(mainDatabase: MainDatabase): NowPlayingMovieIdDao {
        return mainDatabase.nowPlayingMovieIdDao()
    }

    @Provides
    @Singleton
    internal fun provideMovieGenreDao(mainDatabase: MainDatabase): MovieGenreDao {
        return mainDatabase.movieGenreDao()
    }

    @Provides
    @Singleton
    internal fun provideMovieGenreCrossRefDao(mainDatabase: MainDatabase): MovieGenreCrossRefDao {
        return mainDatabase.movieGenreCrossRefDao()
    }

    @Provides
    @Singleton
    internal fun provideTvShowDao(mainDatabase: MainDatabase): TvShowDao {
        return mainDatabase.tvShowDao()
    }

    @Provides
    @Singleton
    internal fun provideTopRatedTvShowIdDao(mainDatabase: MainDatabase): TopRatedTvShowIdDao {
        return mainDatabase.topRatedTvShowIdDao()
    }

    @Provides
    @Singleton
    internal fun provideNowPlayingTvShowIdDao(mainDatabase: MainDatabase): NowPlayingTvShowIdDao {
        return mainDatabase.nowPlayingTvShowIdDao()
    }

    @Provides
    @Singleton
    internal fun provideTvShowGenreDao(mainDatabase: MainDatabase): TvShowGenreDao {
        return mainDatabase.tvShowGenreDao()
    }

    @Provides
    @Singleton
    internal fun provideTvShowGenreCrossRefDao(mainDatabase: MainDatabase): TvShowGenreCrossRefDao {
        return mainDatabase.tvShowGenreCrossRefDao()
    }
}
