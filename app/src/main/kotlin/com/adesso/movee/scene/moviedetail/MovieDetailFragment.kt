package com.adesso.movee.scene.moviedetail

import androidx.navigation.fragment.navArgs
import com.adesso.movee.R
import com.adesso.movee.base.BaseTransparentStatusBarFragment
import com.adesso.movee.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import com.adesso.movee.uimodel.MovieCastUiModel

@AndroidEntryPoint
class MovieDetailFragment :
    BaseTransparentStatusBarFragment<MovieDetailViewModel, FragmentMovieDetailBinding>(),
    MovieCastCallback {

    private val args by navArgs<MovieDetailFragmentArgs>()

    override val layoutId = R.layout.fragment_movie_detail

    override fun initialize() {
        super.initialize()

        binder.movieCastAdapter = MovieCastAdapter(movieCastCallback = this)

        with(viewModel) {
            fetchMovieDetails(args.id)
            fetchMovieCredits(args.id)
        }
    }

    override fun onMovieCastClick(movieCastUiModel: MovieCastUiModel) {
        viewModel.onMovieCastClick(movieCastUiModel)
    }
}
