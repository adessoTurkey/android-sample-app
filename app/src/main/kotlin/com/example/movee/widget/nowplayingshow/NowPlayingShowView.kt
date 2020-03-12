package com.example.movee.widget.nowplayingshow

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.movee.R
import com.example.movee.base.BaseListAdapter
import com.example.movee.databinding.ItemNowPlayingShowBinding
import com.example.movee.databinding.ViewNowPlayingShowBinding
import com.example.movee.internal.extension.executeAfter
import com.example.movee.internal.extension.thisOrEmptyList
import com.example.movee.uimodel.ShowUiModel

@BindingAdapter("submitList")
fun submitList(view: NowPlayingShowView, showUiModelList: List<ShowUiModel>?) {
    view.updateList(showUiModelList ?: emptyList())
}

@BindingAdapter("callback")
fun callback(view: NowPlayingShowView, callback: NowPlayingShowCallback?) {
    callback?.let { view.setNowPlayingShowCallback(it) }
}

class NowPlayingShowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binder = DataBindingUtil.inflate<ViewNowPlayingShowBinding>(
        LayoutInflater.from(context),
        R.layout.view_now_playing_show,
        this,
        true
    )
    private val nowPlayingShowAdapter = NowPlayingShowAdapter()

    private var showUiModelList: List<ShowUiModel>? = null
        set(value) {
            if (value == showUiModelList) {
                return
            }

            field = value.thisOrEmptyList()
            nowPlayingShowAdapter.submitList(showUiModelList)
            binder.viewPagerNowPlayingShow.currentItem = 0
        }

    init {
        orientation = VERTICAL

        with(binder.viewPagerNowPlayingShow) {
            adapter = nowPlayingShowAdapter
            offscreenPageLimit = 3
            orientation = ViewPager2.ORIENTATION_HORIZONTAL

            val pageMargin = resources.getDimensionPixelSize(R.dimen.margin_now_playing_page)
            val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset_now_playing_page)
            setPageTransformer { page, position ->
                val offset = -(2 * pageOffset + pageMargin) * position
                page.translationX = offset
            }

            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        val showUiModel = nowPlayingShowAdapter.getShow(position)
                        binder.executeAfter {
                            show = showUiModel
                        }
                    }
                }
            )
        }
    }

    fun updateList(showUiModels: List<ShowUiModel>) {
        showUiModelList = showUiModels
    }

    override fun setOrientation(orientation: Int) {
        if (orientation != VERTICAL) {
            throw IllegalArgumentException("NowPlayingShowView only supports vertical orientation")
        }
        super.setOrientation(orientation)
    }

    fun setNowPlayingShowCallback(callback: NowPlayingShowCallback) {
        nowPlayingShowAdapter.nowPlayingShowCallback = callback
    }
}

interface NowPlayingShowCallback {

    fun onNowPlayingShowClick(show: ShowUiModel)
}

private class NowPlayingShowAdapter :
    BaseListAdapter<ItemNowPlayingShowBinding, ShowUiModel>() {

    var nowPlayingShowCallback: NowPlayingShowCallback? = null

    override val layoutRes: Int get() = R.layout.item_now_playing_show

    override fun bind(binding: ItemNowPlayingShowBinding, item: ShowUiModel) {
        binding.executeAfter {
            callback = nowPlayingShowCallback
            show = item
        }
    }

    fun getShow(position: Int): ShowUiModel {
        return getItem(position)
    }
}