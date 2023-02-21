package com.adesso.movee.scene.login

import android.content.Intent
import android.net.Uri
import com.adesso.movee.R
import com.adesso.movee.base.BaseTransparentStatusBarFragment
import com.adesso.movee.databinding.FragmentLoginBinding
import com.adesso.movee.internal.util.EventObserver

class LoginFragment : BaseTransparentStatusBarFragment<LoginViewModel, FragmentLoginBinding>() {

    override val layoutId = R.layout.fragment_login

    override fun initialize() {
        super.initialize()

        viewModel.navigateUri.observe(viewLifecycleOwner, handleNavigateUriEvent)
    }

    private val handleNavigateUriEvent = EventObserver<Uri> {
        val intent = Intent(Intent.ACTION_VIEW, it)
        startActivity(intent)
    }
}
