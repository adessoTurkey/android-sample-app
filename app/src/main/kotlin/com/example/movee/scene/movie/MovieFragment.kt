package com.example.movee.scene.movie

import com.example.movee.R
import com.example.movee.base.BaseFragment
import com.example.movee.databinding.FragmentMovieBinding
import com.example.movee.internal.util.addAppBarStateChangeListener
import com.example.movee.uimodel.MovieUiModel
import com.example.movee.uimodel.ShowUiModel
import com.example.movee.widget.nowplayingshow.NowPlayingShowCallback

class MovieFragment :
    BaseFragment<MovieViewModel, FragmentMovieBinding>(),
    PopularMovieCallback,
    NowPlayingShowCallback {

    override val layoutId: Int get() = R.layout.fragment_movie

    override fun initialize() {
        super.initialize()

        binder.popularMovieAdapter = PopularMovieListAdapter(popularMovieCallback = this)
        binder.layoutShowHeader.nowPlayingShowCallback = this
        binder.layoutShowHeader.appBarShow.addAppBarStateChangeListener { _, state ->
            viewModel.appbarStateChanged(state)
        }
    }

    override fun onPopularMovieClick(movie: MovieUiModel) {
        viewModel.onPopularMovieClick(movie)
    }

    override fun onNowPlayingShowClick(show: ShowUiModel) {
        viewModel.onNowPlayingShowClick(show)
    }
}