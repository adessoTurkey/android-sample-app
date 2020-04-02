package com.adesso.movee.internal.injection.module

import com.adesso.movee.internal.injection.scope.LoginScope
import com.adesso.movee.internal.injection.scope.MovieDetailScope
import com.adesso.movee.internal.injection.scope.MovieScope
import com.adesso.movee.internal.injection.scope.PersonDetailScope
import com.adesso.movee.internal.injection.scope.ProfileScope
import com.adesso.movee.internal.injection.scope.SearchScope
import com.adesso.movee.internal.injection.scope.TvShowDetailScope
import com.adesso.movee.internal.injection.scope.TvShowScope
import com.adesso.movee.scene.login.LoginFragment
import com.adesso.movee.scene.login.LoginModule
import com.adesso.movee.scene.movie.MovieFragment
import com.adesso.movee.scene.movie.MovieModule
import com.adesso.movee.scene.moviedetail.MovieDetailFragment
import com.adesso.movee.scene.moviedetail.MovieDetailModule
import com.adesso.movee.scene.persondetail.PersonDetailFragment
import com.adesso.movee.scene.persondetail.PersonDetailModule
import com.adesso.movee.scene.profile.ProfileFragment
import com.adesso.movee.scene.profile.ProfileModule
import com.adesso.movee.scene.search.SearchFragment
import com.adesso.movee.scene.search.SearchModule
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

    @PersonDetailScope
    @ContributesAndroidInjector(modules = [PersonDetailModule::class])
    abstract fun contributePersonDetailFragment(): PersonDetailFragment

    @SearchScope
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun contributeSearchFragment(): SearchFragment

    @ProfileScope
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileFragment(): ProfileFragment

    @LoginScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun contributeLoginFragment(): LoginFragment
}
