package com.adesso.movee.scene.tvshowdetail

import com.adesso.movee.R
import com.adesso.movee.base.BaseListAdapter
import com.adesso.movee.databinding.ItemTvShowCastBinding
import com.adesso.movee.internal.extension.executeAfter
import com.adesso.movee.uimodel.TvShowCastUiModel

interface TvShowCastCallback {

    fun onTvShowCastClick(tvShowCast: TvShowCastUiModel)
}

class TvShowCastAdapter(private val tvShowCastCallback: TvShowCastCallback) :
    BaseListAdapter<ItemTvShowCastBinding, TvShowCastUiModel>() {

    override val layoutRes: Int get() = R.layout.item_tv_show_cast

    override fun bind(binding: ItemTvShowCastBinding, item: TvShowCastUiModel) {
        binding.executeAfter {
            callback = tvShowCastCallback
            tvShowCast = item
        }
    }
}