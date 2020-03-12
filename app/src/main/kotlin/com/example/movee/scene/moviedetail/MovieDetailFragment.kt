package com.example.movee.scene.moviedetail

import androidx.navigation.fragment.navArgs
import com.example.movee.R
import com.example.movee.base.BaseTransparentStatusBarFragment
import com.example.movee.databinding.FragmentMovieDetailBinding

class MovieDetailFragment :
    BaseTransparentStatusBarFragment<MovieDetailViewModel, FragmentMovieDetailBinding>() {

    private val args by navArgs<MovieDetailFragmentArgs>()

    override val layoutId = R.layout.fragment_movie_detail

    override fun initialize() {
        super.initialize()

        with(viewModel) {
            fetchMovieDetails(args.show)
            fetchMovieCredits(args.show)
        }
    }
}
