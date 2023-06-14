package com.adesso.movee.scene.moviedetail

import com.adesso.movee.R
import com.adesso.movee.base.BaseListAdapter
import com.adesso.movee.databinding.ItemMovieCastBinding
import com.adesso.movee.internal.extension.executeAfter
import com.adesso.movee.uimodel.MovieCastUiModel

interface MovieCastCallback {

    fun onMovieCastClick(movieCastUiModel: MovieCastUiModel)
}

class MovieCastAdapter(private val movieCastCallback: MovieCastCallback) :
    BaseListAdapter<ItemMovieCastBinding, MovieCastUiModel>() {

    override val layoutRes: Int get() = R.layout.item_movie_cast

    override fun bind(binding: ItemMovieCastBinding, item: MovieCastUiModel) {
        binding.executeAfter {
            callback = movieCastCallback
            movieCast = item
        }
    }
}
