package com.adesso.movee.scene.search

import com.adesso.movee.R
import com.adesso.movee.base.BaseListAdapter
import com.adesso.movee.databinding.ItemMultiSearchBinding
import com.adesso.movee.internal.extension.executeAfter
import com.adesso.movee.uimodel.MultiSearchUiModel

interface MultiSearchCallback {

    fun onMultiSearchClick(multiSearch: MultiSearchUiModel)
}

class MultiSearchAdapter(
    private val multiSearchCallback: MultiSearchCallback
) : BaseListAdapter<ItemMultiSearchBinding, MultiSearchUiModel>() {

    override val layoutRes: Int get() = R.layout.item_multi_search

    override fun bind(binding: ItemMultiSearchBinding, item: MultiSearchUiModel) {
        binding.executeAfter {
            callback = multiSearchCallback
            multiSearch = item
        }
    }
}