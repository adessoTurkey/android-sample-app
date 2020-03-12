package com.example.movee.scene.tvshow

import com.example.movee.R
import com.example.movee.base.BaseListAdapter
import com.example.movee.databinding.ItemTopRatedTvShowBinding
import com.example.movee.internal.extension.executeAfter
import com.example.movee.uimodel.TvShowUiModel

interface TopRatedTvShowCallback {

    fun onTopRatedTvShowClick(tvShow: TvShowUiModel)
}

class TopRatedTvShowListAdapter(private val topRatedTvShowCallback: TopRatedTvShowCallback) :
    BaseListAdapter<ItemTopRatedTvShowBinding, TvShowUiModel>() {

    override val layoutRes: Int get() = R.layout.item_top_rated_tv_show

    override fun bind(binding: ItemTopRatedTvShowBinding, item: TvShowUiModel) {
        binding.executeAfter {
            callback = topRatedTvShowCallback
            tvShow = item
        }
    }
}