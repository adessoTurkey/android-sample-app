package com.adesso.movee.internal.injection.module

import com.adesso.movee.internal.injection.scope.MovieDetailScope
import com.adesso.movee.internal.injection.scope.MovieScope
import com.adesso.movee.internal.injection.scope.TvShowDetailScope
import com.adesso.movee.internal.injection.scope.TvShowScope
import com.adesso.movee.scene.movie.MovieFragment
import com.adesso.movee.scene.movie.MovieModule
import com.adesso.movee.scene.moviedetail.MovieDetailFragment
import com.adesso.movee.scene.moviedetail.MovieDetailModule
import com.adesso.movee.scene.tvshow.TvShowFragment
import com.adesso.movee.scene.tvshow.TvShowModule
import com.adesso.movee.scene.tvshowdetail.TvShowDetailFragment
import com.adesso.movee.scene.tvshowdetail.TvShowDetailModule
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
