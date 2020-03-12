package com.adesso.movee.internal.injection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adesso.movee.scene.main.MainViewModel
import com.adesso.movee.scene.splash.SplashViewModel
import com.adesso.movee.scene.movie.MovieViewModel
import com.adesso.movee.scene.moviedetail.MovieDetailViewModel
import com.adesso.movee.scene.tvshow.TvShowViewModel
import com.adesso.movee.scene.tvshowdetail.TvShowDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindsSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindsMovieViewModel(viewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvShowViewModel::class)
    abstract fun bindsTvShowViewModel(viewModel: TvShowViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvShowDetailViewModel::class)
    abstract fun bindsTvShowDetailViewModel(viewModel: TvShowDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindsMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel
}
