package com.example.movee.scene.tvshow

import com.example.movee.R
import com.example.movee.base.BaseFragment
import com.example.movee.databinding.FragmentTvShowBinding
import com.example.movee.internal.util.addAppBarStateChangeListener
import com.example.movee.uimodel.ShowUiModel
import com.example.movee.uimodel.TvShowUiModel
import com.example.movee.widget.nowplayingshow.NowPlayingShowCallback

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