package com.adesso.movee.scene.tvshow

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentTvShowBinding
import com.adesso.movee.internal.util.addAppBarStateChangeListener
import com.adesso.movee.scene.login.Login
import com.adesso.movee.scene.login.LoginViewModel
import com.adesso.movee.uimodel.ShowUiModel
import com.adesso.movee.uimodel.TvShowUiModel
import com.adesso.movee.widget.nowplayingshow.NowPlayingShowCallback

class TvShowFragment :
    BaseFragment<TvShowViewModel, FragmentTvShowBinding>(),
    TopRatedTvShowCallback,
    NowPlayingShowCallback {

    override val layoutId: Int get() = R.layout.fragment_tv_show

    override fun initialize() {
        super.initialize()
        binder.composeViewTvShow.setContent {
            TvShowList(viewModel)
        }
        binder.topRatedTvShowAdapter = TopRatedTvShowListAdapter(topRatedTvShowCallback = this)
        binder.layoutShowHeader.nowPlayingShowCallback = this
        binder.layoutShowHeader.appBarShow.addAppBarStateChangeListener { _, state ->
            viewModel.onAppBarStateChanged(state)
        }
    }

    override fun onTopRatedTvShowClick(tvShow: TvShowUiModel) {
        viewModel.onTopRatedTvShowClick(tvShow)
    }

    override fun onNowPlayingShowClick(show: ShowUiModel) {
        viewModel.onNowPlayingShowClick(show)
    }

    @Preview
    @Composable
    fun TvShowListPreview() {
        TvShowList(viewModel)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TvShowList(viewModel: TvShowViewModel) {
    val tvShowList by viewModel.topRatedTvShows.observeAsState()
    tvShowList?.let { list ->
        LazyVerticalGrid(
            cells = GridCells.Adaptive(minSize = 128.dp),
            content = {
                items(list.size) { index ->
                    Card(
                        backgroundColor = Color.Red,
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.margin_extra_small))
                            .fillMaxWidth(),
                        elevation = dimensionResource(id = R.dimen.margin_small),
                    ) {
                        Text(
                            text = list[index].title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_large))
                        )
                    }
                }
            }
        )
    }
}
