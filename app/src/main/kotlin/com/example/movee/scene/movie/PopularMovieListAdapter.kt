package com.example.movee.scene.movie

import com.example.movee.R
import com.example.movee.base.BaseListAdapter
import com.example.movee.databinding.ItemPopularMovieBinding
import com.example.movee.internal.extension.executeAfter
import com.example.movee.uimodel.MovieUiModel

interface PopularMovieCallback {

    fun onPopularMovieClick(movie: MovieUiModel)
}

class PopularMovieListAdapter(private val popularMovieCallback: PopularMovieCallback)
    : BaseListAdapter<ItemPopularMovieBinding, MovieUiModel>() {

    override val layoutRes: Int get() = R.layout.item_popular_movie

    override fun bind(binding: ItemPopularMovieBinding, item: MovieUiModel) {
        binding.executeAfter {
            callback = popularMovieCallback
            movie = item
        }
    }
}





