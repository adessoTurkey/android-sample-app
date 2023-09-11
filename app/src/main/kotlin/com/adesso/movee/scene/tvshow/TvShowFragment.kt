package com.adesso.movee.scene.tvshow

import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentTvShowBinding
import com.adesso.movee.internal.extension.collectFlow
import com.adesso.movee.internal.extension.toast
import com.adesso.movee.internal.util.addAppBarStateChangeListener
import com.adesso.movee.uimodel.ShowUiModel
import com.adesso.movee.uimodel.TvShowUiModel
import com.adesso.movee.widget.nowplayingshow.NowPlayingShowCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowFragment :
    BaseFragment<TvShowViewModel, FragmentTvShowBinding>(),
    TopRatedTvShowCallback,
    NowPlayingShowCallback {

    override val layoutId: Int get() = R.layout.fragment_tv_show

    override fun initialize() {
        super.initialize()

        binder.topRatedTvShowAdapter = TopRatedTvShowPagingAdapter(topRatedTvShowCallback = this)
        binder.layoutShowHeader.nowPlayingShowCallback = this
        binder.layoutShowHeader.appBarShow.addAppBarStateChangeListener { _, state ->
            viewModel.onAppBarStateChanged(state)
        }
        setShouldRefreshPagingListener()
    }

    override fun onTopRatedTvShowClick(tvShow: TvShowUiModel) {
        viewModel.onTopRatedTvShowClick(tvShow)
    }

    override fun onShowClick(show: ShowUiModel) {
        viewModel.onNowPlayingShowClick(show)
    }

    private fun setShouldRefreshPagingListener() {
        collectFlow(viewModel.shouldRefreshPaging) {
            if (it) {
                binder.topRatedTvShowAdapter?.refresh()
                binder.layoutShowHeader.nowPlayingShowView.nowPlayingShowAdapter.refresh()
                requireContext().toast(getString(R.string.common_paging_list_refreshed_message))
                binder.recyclerViewTopRatedTvShow.scrollToPosition(0)
                binder.layoutShowHeader.nowPlayingShowView.setCurrentItem(0)
            }
        }
    }
}
