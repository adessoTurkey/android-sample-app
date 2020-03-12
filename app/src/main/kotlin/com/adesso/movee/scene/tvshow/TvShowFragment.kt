package com.adesso.movee.scene.tvshow

import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentTvShowBinding
import com.adesso.movee.internal.util.addAppBarStateChangeListener
import com.adesso.movee.uimodel.ShowUiModel
import com.adesso.movee.uimodel.TvShowUiModel
import com.adesso.movee.widget.nowplayingshow.NowPlayingShowCallback

class TvShowFragment :
    BaseFragment<TvShowViewModel, FragmentTvShowBinding>(),
    TopRatedTvShowCallback,
    NowPlayingShowCallback {

    override val layoutId: Int get() = R.layout.fragment_tv_show

    override fun initialize() {
        super.initialize()

        binder.topRatedTvShowAdapter = TopRatedTvShowListAdapter(topRatedTvShowCallback = this)
        binder.layoutShowHeader.nowPlayingShowCallback = this
        binder.layoutShowHeader.appBarShow.addAppBarStateChangeListener { _, state ->
            viewModel.onAppBarStateChanged(state)
        }
    }

    override fun onTopRatedTvShowClick(tvShow: TvShowUiModel) {
        viewModel.onTopRatedTvShowClick(tvShow)
    }

    override fun onNowPlayingShowClick(show: ShowUiModel) {
        viewModel.onNowPlayingShowClick(show)
    }
}