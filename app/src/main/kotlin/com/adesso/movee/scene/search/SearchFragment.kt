package com.adesso.movee.scene.search

import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentSearchBinding
import com.adesso.movee.scene.search.jetpack.SearchScreenComposable
import dagger.hilt.android.AndroidEntryPoint

// TODO: Remove all classes which related SearchFragment after all classes refactored jetpack compose.
@AndroidEntryPoint
class SearchFragment :
    BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    override val layoutId = R.layout.fragment_search

    override fun initialize() {
        super.initialize()

        binder.composeView.setContent {
            SearchScreenComposable()
        }
    }
}
