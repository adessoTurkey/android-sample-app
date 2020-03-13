package com.adesso.movee.scene.persondetail

import androidx.navigation.fragment.navArgs
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentPersonDetailBinding
import com.adesso.movee.internal.util.addAppBarStateChangeListener

class PersonDetailFragment : BaseFragment<PersonDetailViewModel, FragmentPersonDetailBinding>() {

    private val args by navArgs<PersonDetailFragmentArgs>()

    override val layoutId = R.layout.fragment_person_detail

    override fun initialize() {
        super.initialize()

        binder.appBarProfile.addAppBarStateChangeListener { _, state ->
            viewModel.onProfileAppBarStateChange(state)
        }

        viewModel.fetchPersonDetails(args.personId)
    }
}
