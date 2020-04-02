package com.adesso.movee.widget.expandabletextview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.adesso.movee.R
import com.adesso.movee.databinding.ViewExpandableTextBinding
import com.adesso.movee.internal.extension.gone
import com.adesso.movee.internal.extension.show

@BindingAdapter("etv_content")
fun setContent(view: ExpandableTextView, content: String?) {
    view.content = content
}

class ExpandableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var collapseLines = -1
    private var collapsedToggleText: String? = null
    private var expandedToggleText: String? = null
    private var expanded = false
    private val binder = ViewExpandableTextBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )
    var content: String? = null
        set(value) {
            field = value
            with(binder.textViewContent) {
                text = content
                post {
                    if (collapseLines > 0 && lineCount > collapseLines) {
                        binder.textViewToggle.show()
                        collapse()
                    } else {
                        binder.textViewToggle.gone()
                    }
                }
            }
        }

    init {
        orientation = VERTICAL

        readAttributes(attrs)

        binder.textViewToggle.setOnClickListener {
            if (expanded) collapse() else expand()
            expanded = !expanded
        }
    }

    private fun readAttributes(attrs: AttributeSet?) {
        context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.ExpandableTextView,
            0,
            0
        )?.apply {

            if (hasValue(R.styleable.ExpandableTextView_etv_contentTextAppearance)) {
                TextViewCompat.setTextAppearance(
                    binder.textViewContent,
                    getResourceId(R.styleable.ExpandableTextView_etv_contentTextAppearance, 0)
                )
            }

            if (hasValue(R.styleable.ExpandableTextView_etv_toggleTextAppearance)) {
                TextViewCompat.setTextAppearance(
                    binder.textViewToggle,
                    getResourceId(R.styleable.ExpandableTextView_etv_toggleTextAppearance, 0)
                )
            }

            collapsedToggleText = getString(R.styleable.ExpandableTextView_etv_collapsedText)
            expandedToggleText = getString(R.styleable.ExpandableTextView_etv_expandedText)
            collapseLines = getInt(R.styleable.ExpandableTextView_etv_collapseLines, 0)
        }
    }

    private fun collapse() {
        with(binder) {
            textViewContent.maxLines = collapseLines
            textViewToggle.text = collapsedToggleText
        }
    }

    private fun expand() {
        with(binder) {
            textViewContent.maxLines = Integer.MAX_VALUE
            textViewToggle.text = expandedToggleText
        }
    }

    override fun setOrientation(orientation: Int) {
        check(orientation == VERTICAL) { "ExpandableTextView supports only vertical orientation" }
        super.setOrientation(orientation)
    }
}
