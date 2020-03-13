package com.adesso.movee.scene.tvshowdetail

import androidx.navigation.fragment.navArgs
import com.adesso.movee.R
import com.adesso.movee.base.BaseTransparentStatusBarFragment
import com.adesso.movee.databinding.FragmentTvShowDetailBinding
import com.adesso.movee.uimodel.TvShowCastUiModel

class TvShowDetailFragment :
    BaseTransparentStatusBarFragment<TvShowDetailViewModel, FragmentTvShowDetailBinding>(),
    TvShowCastCallback {

    private val args by navArgs<TvShowDetailFragmentArgs>()

    override val layoutId = R.layout.fragment_tv_show_detail

    override fun initialize() {
        super.initialize()

        binder.tvShowCastAdapter = TvShowCastAdapter(tvShowCastCallback = this)

        with(viewModel) {
            fetchTvShowDetail(args.show)
            fetchTvShowCredits(args.show)
        }
    }

    override fun onTvShowCastClick(tvShowCast: TvShowCastUiModel) {
        viewModel.onTvShowCastClick(tvShowCast)
    }
}
