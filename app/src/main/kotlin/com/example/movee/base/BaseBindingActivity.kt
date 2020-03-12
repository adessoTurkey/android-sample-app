package com.example.movee.base

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.movee.internal.util.functional.lazyThreadSafetyNone

abstract class BaseBindingActivity<VM : BaseAndroidViewModel, B : ViewDataBinding> :
    BaseActivity<VM>() {

    protected val binder by lazyThreadSafetyNone<B> {
        DataBindingUtil.setContentView(this, layoutId)
    }

    @get:LayoutRes
    abstract val layoutId: Int
}