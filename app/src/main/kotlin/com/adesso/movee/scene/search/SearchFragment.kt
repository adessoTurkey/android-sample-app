package com.adesso.movee.scene.search

import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentSearchBinding
import com.adesso.movee.internal.databinding.TextChangeCallback
import com.adesso.movee.uimodel.MultiSearchUiModel

class SearchFragment :
    BaseFragment<SearchViewModel, FragmentSearchBinding>(),
    MultiSearchCallback,
    TextChangeCallback {

    override val layoutId = R.layout.fragment_search

    override fun initialize() {
        super.initialize()

        binder.multiSearchAdapter = MultiSearchAdapter(multiSearchCallback = this)
        binder.textChangeCallback = this
    }

    override fun onMultiSearchClick(multiSearch: MultiSearchUiModel) {
        viewModel.onMultiSearchClick(multiSearch)
    }

    override fun onTextChange(text: String?) {
        viewModel.onTextChange(text)
    }
}
