package com.adesso.movee.scene.movie

import com.adesso.movee.R
import com.adesso.movee.base.BasePagingAdapter
import com.adesso.movee.databinding.ItemPopularMovieBinding
import com.adesso.movee.internal.extension.executeAfter
import com.adesso.movee.uimodel.MovieUiModel

interface PopularMovieCallback {

    fun onPopularMovieClick(movie: MovieUiModel)
}

class PopularMovieListAdapter(
    private val popularMovieCallback: PopularMovieCallback,
) : BasePagingAdapter<ItemPopularMovieBinding, MovieUiModel>() {

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override val layoutRes: Int get() = R.layout.item_popular_movie

    override fun bind(binding: ItemPopularMovieBinding, item: MovieUiModel) {
        binding.executeAfter {
            callback = popularMovieCallback
            movie = item
        }
    }
}
