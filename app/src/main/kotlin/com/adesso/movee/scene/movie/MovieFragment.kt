package com.adesso.movee.scene.movie

import androidx.lifecycle.lifecycleScope
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentMovieBinding
import com.adesso.movee.internal.util.addAppBarStateChangeListener
import com.adesso.movee.uimodel.MovieUiModel
import com.adesso.movee.uimodel.ShowUiModel
import com.adesso.movee.widget.nowplayingshow.NowPlayingShowCallback
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        collectPagedPopularMovies()
    }

    override fun onPopularMovieClick(movie: MovieUiModel) {
        viewModel.onPopularMovieClick(movie)
    }

    private fun collectPagedPopularMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMovies.collectLatest {
                binder.popularMovieAdapter!!.submitData(it)
            }
        }
    }

    override fun onNowPlayingShowClick(show: ShowUiModel) {
        viewModel.onNowPlayingShowClick(show)
    }
}
