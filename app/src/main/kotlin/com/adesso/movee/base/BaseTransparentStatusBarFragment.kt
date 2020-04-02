package com.adesso.movee.base

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.adesso.movee.R

abstract class BaseTransparentStatusBarFragment<VM : BaseAndroidViewModel, B : ViewDataBinding> :
    BaseFragment<VM, B>() {

    private var systemUiBeforeChange: Int = 0
    private var statusBarColorBeforeChange: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.let { window ->
            systemUiBeforeChange = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = systemUiBeforeChange or SYSTEM_UI_TRANSPARENT

            statusBarColorBeforeChange = window.statusBarColor
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.transparent_20)
        }
    }

    override fun onDestroy() {
        activity?.window?.let { window ->
            window.statusBarColor = statusBarColorBeforeChange
            window.decorView.systemUiVisibility = systemUiBeforeChange
        }

        super.onDestroy()
    }

    companion object {
        private const val SYSTEM_UI_TRANSPARENT =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
}
