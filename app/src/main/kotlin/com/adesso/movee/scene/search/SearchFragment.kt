package com.adesso.movee.scene.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnLifecycleDestroyed
import androidx.compose.ui.tooling.preview.Preview
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentSearchBinding
import com.adesso.movee.internal.databinding.TextChangeCallback
import com.adesso.movee.scene.search.components.SearchScreen
import com.adesso.movee.uimodel.MultiSearchUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SearchFragment :
    BaseFragment<SearchViewModel, FragmentSearchBinding>(),
    TextChangeCallback {

    override val layoutId = R.layout.fragment_search

    @ExperimentalCoroutinesApi
    @ExperimentalComposeUiApi
    @ExperimentalFoundationApi
    override fun initialize() {
        super.initialize()
        binder.composeViewSearch.setViewCompositionStrategy(
            DisposeOnLifecycleDestroyed(viewLifecycleOwner)
        )
        binder.composeViewSearch.setContent {
            SearchScreen(viewModel)
        }
    }

    fun onMultiSearchClick(multiSearch: MultiSearchUiModel) {
        viewModel.onMultiSearchClick(multiSearch)
    }

    override fun onTextChange(text: String?) {
        viewModel.onTextChange(text)
    }
}
