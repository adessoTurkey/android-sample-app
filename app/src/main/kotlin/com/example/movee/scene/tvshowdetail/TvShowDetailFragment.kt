package com.example.movee.scene.tvshowdetail

import androidx.navigation.fragment.navArgs
import com.example.movee.R
import com.example.movee.base.BaseTransparentStatusBarFragment
import com.example.movee.databinding.FragmentTvShowDetailBinding

class TvShowDetailFragment :
    BaseTransparentStatusBarFragment<TvShowDetailViewModel, FragmentTvShowDetailBinding>() {

    private val args by navArgs<TvShowDetailFragmentArgs>()

    override val layoutId = R.layout.fragment_tv_show_detail

    override fun initialize() {
        super.initialize()

        viewModel.fetchTvShowDetail(args.show)
    }
}
