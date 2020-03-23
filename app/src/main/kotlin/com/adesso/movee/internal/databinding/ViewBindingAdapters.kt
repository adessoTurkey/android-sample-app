package com.adesso.movee.internal.databinding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.adesso.movee.R
import com.adesso.movee.base.BaseListAdapter
import com.adesso.movee.base.ListAdapterItem
import com.adesso.movee.internal.extension.loadImage
import com.adesso.movee.internal.extension.setOnDrawableEndClickListener
import com.adesso.movee.internal.util.GridLayoutSpaceItemDecoration

@BindingAdapter("lottieFile")
fun setLottieFile(view: LottieAnimationView, resource: String) {
    view.setAnimation(resource)
}

@BindingAdapter("hideIfNull")
fun setVisible(view: View, obj: Any?) {
    view.visibility = if (obj == null) {
        View.GONE
    } else if (obj is String && obj.isBlank()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("visibleIf")
fun visibleIf(view: View, shouldVisible: Boolean) {
    view.visibility = if (shouldVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("submitList")
fun submitList(view: RecyclerView, list: List<ListAdapterItem>?) {
    val adapter = view.adapter as BaseListAdapter<ViewDataBinding, ListAdapterItem>?
    adapter?.submitList(list)
}

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: BaseListAdapter<ViewDataBinding, ListAdapterItem>?) {
    adapter?.let {
        view.adapter = it
    }
}

@BindingAdapter("spaceItemDecoration")
fun addSpaceItemDecoration(view: RecyclerView, @Dimension space: Float) {
    val spaceItemDecoration = GridLayoutSpaceItemDecoration(space.toInt())
    view.addItemDecoration(spaceItemDecoration)
}

@BindingAdapter("imageFromUrl", "placeholderRes", "errorRes", requireAll = false)
fun setImage(
    view: ImageView,
    url: String?,
    @DrawableRes placeholderRes: Int?,
    @DrawableRes errorRes: Int?
) {
    val defaultDrawable = R.drawable.ic_movee_placeholder

    view.loadImage(
        url,
        placeholderRes ?: defaultDrawable,
        errorRes ?: defaultDrawable
    )
}

@BindingAdapter("setClearable")
fun setClearable(
    view: EditText,
    clearDrawable: Drawable
) {
    with(view) {
        val updateEndDrawable: () -> Unit = {
            val currentDrawables = compoundDrawablesRelative

            setCompoundDrawablesRelativeWithIntrinsicBounds(
                currentDrawables[0],
                currentDrawables[1],
                if (text.isNullOrBlank()) null else clearDrawable,
                currentDrawables[3]
            )
        }

        updateEndDrawable()

        doAfterTextChanged {
            updateEndDrawable()
        }

        setOnDrawableEndClickListener {
            text = null
        }
    }
}