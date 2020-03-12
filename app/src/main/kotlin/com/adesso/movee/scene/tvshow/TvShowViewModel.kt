package com.adesso.movee.scene.tvshow

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adesso.movee.R
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchNowPlayingTvShowsUseCase
import com.adesso.movee.domain.FetchTopRatedTvShowsUseCase
import com.adesso.movee.internal.util.AppBarStateChangeListener
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.COLLAPSED
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.EXPANDED
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.IDLE
import com.adesso.movee.internal.util.TripleCombinedLiveData
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.ShowHeaderUiModel
import com.adesso.movee.uimodel.ShowUiModel
import com.adesso.movee.uimodel.TvShowUiModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvShowViewModel @Inject constructor(
    private val fetchTopRatedTvShowsUseCase: FetchTopRatedTvShowsUseCase,
    private val fetchNowPlayingTvShowsUseCase: FetchNowPlayingTvShowsUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _topRatedTvShows = MutableLiveData<List<TvShowUiModel>>()
    private val _toolbarTitle = MutableLiveData<String>()
    private val _toolbarSubtitle = MutableLiveData(getString(R.string.top_rated))
    private val _nowPlayingTvShows = MutableLiveData<List<TvShowUiModel>>()
    val topRatedTvShows: LiveData<List<TvShowUiModel>> get() = _topRatedTvShows
    val showHeader = TripleCombinedLiveData(
        _toolbarTitle,
        _toolbarSubtitle,
        _nowPlayingTvShows
    ) { title, subtitle, nowPlayingShows ->
        ShowHeaderUiModel(
            title,
            subtitle,
            nowPlayingShows
        )
    }

    init {
        fetchTopRatedTvShows()
        fetchNowPlayingTvShows()
    }

    private fun fetchTopRatedTvShows() {
        bgScope.launch {
            val topRatedTvShowsResult = fetchTopRatedTvShowsUseCase.run(UseCase.None)

            onUIThread {
                topRatedTvShowsResult.either(::handleFailure, ::postTopRatedTvShows)
            }
        }
    }

    private fun postTopRatedTvShows(tvShows: List<TvShowUiModel>) {
        _topRatedTvShows.value = tvShows
    }

    private fun fetchNowPlayingTvShows() {
        bgScope.launch {
            val nowPlayingTvShowsResult = fetchNowPlayingTvShowsUseCase.run(UseCase.None)

            onUIThread {
                nowPlayingTvShowsResult.either(::handleFailure, ::postNowPlayingTvShows)
            }
        }
    }

    private fun postNowPlayingTvShows(tvShows: List<TvShowUiModel>) {
        _nowPlayingTvShows.value = tvShows
    }

    fun onAppBarStateChanged(state: AppBarStateChangeListener.State) {
        val titleRes = when (state) {
            COLLAPSED -> R.string.top_rated_series
            EXPANDED, IDLE -> R.string.tv_series
        }

        postToolbarTitle(getString(titleRes))
    }

    private fun postToolbarTitle(title: String) {
        _toolbarTitle.value = title
    }

    fun onTopRatedTvShowClick(tvShow: TvShowUiModel) {
        navigateTvShowDetailFragment(tvShow)
    }

    fun onNowPlayingShowClick(show: ShowUiModel) {
        navigateTvShowDetailFragment(show)
    }

    private fun navigateTvShowDetailFragment(show: ShowUiModel) {
        navigate(TvShowFragmentDirections.toTvShowDetail(show))
    }
}