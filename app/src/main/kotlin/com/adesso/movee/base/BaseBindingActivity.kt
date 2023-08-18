package com.adesso.movee.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.adesso.movee.internal.util.functional.lazyThreadSafetyNone

abstract class BaseBindingActivity<B : ViewDataBinding> : AppCompatActivity() {

    protected val binder by lazyThreadSafetyNone<B> {
        DataBindingUtil.setContentView(this, layoutId)
    }

    @get:LayoutRes
    abstract val layoutId: Int
}
