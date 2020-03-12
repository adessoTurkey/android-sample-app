package com.example.movee.internal.injection.module

import com.example.movee.internal.injection.scope.MovieDetailScope
import com.example.movee.internal.injection.scope.MovieScope
import com.example.movee.internal.injection.scope.TvShowDetailScope
import com.example.movee.internal.injection.scope.TvShowScope
import com.example.movee.scene.movie.MovieFragment
import com.example.movee.scene.movie.MovieModule
import com.example.movee.scene.moviedetail.MovieDetailFragment
import com.example.movee.scene.moviedetail.MovieDetailModule
import com.example.movee.scene.tvshow.TvShowFragment
import com.example.movee.scene.tvshow.TvShowModule
import com.example.movee.scene.tvshowdetail.TvShowDetailFragment
import com.example.movee.scene.tvshowdetail.TvShowDetailModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentsModule {

    @MovieScope
    @ContributesAndroidInjector(modules = [MovieModule::class])
    abstract fun contributeMovieFragment(): MovieFragment

    @TvShowScope
    @ContributesAndroidInjector(modules = [TvShowModule::class])
    abstract fun contributeTvShowFragment(): TvShowFragment

    @TvShowDetailScope
    @ContributesAndroidInjector(modules = [TvShowDetailModule::class])
    abstract fun contributeTvShowDetailFragment(): TvShowDetailFragment

    @MovieDetailScope
    @ContributesAndroidInjector(modules = [MovieDetailModule::class])
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment
}
