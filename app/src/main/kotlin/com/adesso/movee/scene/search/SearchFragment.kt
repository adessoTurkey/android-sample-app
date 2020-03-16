package com.adesso.movee.scene.search

import androidx.core.widget.doAfterTextChanged
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentSearchBinding
import com.adesso.movee.uimodel.MultiSearchUiModel

class SearchFragment :
    BaseFragment<SearchViewModel, FragmentSearchBinding>(),
    MultiSearchCallback {

    override val layoutId = R.layout.fragment_search

    override fun initialize() {
        super.initialize()

        binder.multiSearchAdapter = MultiSearchAdapter(multiSearchCallback = this)
        binder.editTextSearch.doAfterTextChanged { editable ->
            viewModel.onQueryChange(editable?.toString())
        }
    }

    override fun onMultiSearchClick(multiSearch: MultiSearchUiModel) {
        viewModel.onMultiSearchClick(multiSearch)
    }
}
