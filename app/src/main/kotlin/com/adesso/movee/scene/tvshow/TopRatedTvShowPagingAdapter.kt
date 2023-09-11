package com.adesso.movee.scene.tvshow

import com.adesso.movee.R
import com.adesso.movee.base.BasePagingAdapter
import com.adesso.movee.databinding.ItemTopRatedTvShowBinding
import com.adesso.movee.internal.extension.executeAfter
import com.adesso.movee.uimodel.TvShowUiModel

interface TopRatedTvShowCallback {

    fun onTopRatedTvShowClick(tvShow: TvShowUiModel)
}

class TopRatedTvShowPagingAdapter(private val topRatedTvShowCallback: TopRatedTvShowCallback) :
    BasePagingAdapter<ItemTopRatedTvShowBinding, TvShowUiModel>() {

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override val layoutRes: Int get() = R.layout.item_top_rated_tv_show

    override fun bind(binding: ItemTopRatedTvShowBinding, item: TvShowUiModel) {
        binding.executeAfter {
            callback = topRatedTvShowCallback
            tvShow = item
        }
    }
}
