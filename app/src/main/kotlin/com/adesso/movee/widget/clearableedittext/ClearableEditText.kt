package com.adesso.movee.widget.clearableedittext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.adesso.movee.R

class ClearableEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    private var clearDrawable: Drawable? = null

    init {
        clearDrawable = ContextCompat.getDrawable(context, DRAWABLE_CLEAR)

        doAfterTextChanged {
            updateEndDrawable(if (text.isNullOrBlank()) null else clearDrawable)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP &&
            event.x >= width - totalPaddingEnd
        ) {
            text = null
        }

        return super.onTouchEvent(event)
    }

    private fun updateEndDrawable(endDrawable: Drawable?) {
        val currentDrawables = compoundDrawablesRelative

        setCompoundDrawablesRelativeWithIntrinsicBounds(
            currentDrawables[INDEX_DRAWABLE_START],
            currentDrawables[INDEX_DRAWABLE_TOP],
            endDrawable,
            currentDrawables[INDEX_DRAWABLE_BOTTOM]
        )
    }

    companion object {
        private const val DRAWABLE_CLEAR = R.drawable.ic_clear
        private const val INDEX_DRAWABLE_START = 0
        private const val INDEX_DRAWABLE_TOP = 1
        private const val INDEX_DRAWABLE_END = 2
        private const val INDEX_DRAWABLE_BOTTOM = 3
    }
}