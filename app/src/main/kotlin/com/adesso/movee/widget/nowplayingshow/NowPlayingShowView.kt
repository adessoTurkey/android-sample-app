package com.adesso.movee.widget.nowplayingshow

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.paging.PagingData
import androidx.viewpager2.widget.ViewPager2
import com.adesso.movee.R
import com.adesso.movee.base.BasePagingAdapter
import com.adesso.movee.base.ListAdapterItem
import com.adesso.movee.databinding.ItemNowPlayingShowBinding
import com.adesso.movee.databinding.ViewNowPlayingShowBinding
import com.adesso.movee.internal.extension.executeAfter
import com.adesso.movee.uimodel.ShowUiModel

@Suppress("UNCHECKED_CAST")
@BindingAdapter("submitList")
fun <T : ListAdapterItem> submitList(view: NowPlayingShowView, data: PagingData<T>) {
    val adapter = view.nowPlayingShowAdapter as BasePagingAdapter<ViewDataBinding, T>?

    if (ViewTreeLifecycleOwner.get(view)?.lifecycle == null) return

    adapter?.submitData(ViewTreeLifecycleOwner.get(view)!!.lifecycle, data)
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
    val nowPlayingShowAdapter = NowPlayingShowAdapter()

    init {
        orientation = VERTICAL

        with(binder.viewPagerHeaderShow) {
            adapter = nowPlayingShowAdapter
            offscreenPageLimit = 20
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
                        if (nowPlayingShowAdapter.itemCount == 0) return

                        val showUiModel =
                            nowPlayingShowAdapter.getShow(position)

                        binder.executeAfter {
                            show = showUiModel
                        }
                    }
                }
            )
        }
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

    fun setCurrentItem(index: Int) {
        binder.viewPagerHeaderShow.currentItem = index
    }
}

interface NowPlayingShowCallback {

    fun onShowClick(show: ShowUiModel)
}

class NowPlayingShowAdapter :
    BasePagingAdapter<ItemNowPlayingShowBinding, ShowUiModel>() {

    var nowPlayingShowCallback: NowPlayingShowCallback? = null

    override val layoutRes: Int get() = R.layout.item_now_playing_show

    override fun bind(binding: ItemNowPlayingShowBinding, item: ShowUiModel) {
        binding.executeAfter {
            callback = nowPlayingShowCallback
            show = item
        }
    }

    fun getShow(position: Int): ShowUiModel? {
        return getItem(position)
    }
}
