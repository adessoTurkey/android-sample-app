package com.adesso.movee.scene.tvshow

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

class TvShowViewModel @Inject constructor(
    private val fetchTopRatedTvShowsUseCase: FetchTopRatedTvShowsUseCase,
    private val fetchNowPlayingTvShowsUseCase: FetchNowPlayingTvShowsUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _topRatedTvShows = MutableLiveData<List<TvShowUiModel>>()
    private val _toolbarTitle = MutableLiveData<String>()
    private val _toolbarSubtitle = MutableLiveData(getString(R.string.tv_show_message_top_rated))
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
        fetchTopRatedTvShowsUseCase.invoke(viewModelScope, UseCase.None) {
            it.either(::handleFailure, ::postTopRatedTvShows)
        }
    }

    private fun postTopRatedTvShows(tvShows: List<TvShowUiModel>) {
        _topRatedTvShows.value = tvShows
    }

    private fun fetchNowPlayingTvShows() {
        fetchNowPlayingTvShowsUseCase.invoke(viewModelScope, UseCase.None) {
            it.either(::handleFailure, ::postNowPlayingTvShows)
        }
    }

    private fun postNowPlayingTvShows(tvShows: List<TvShowUiModel>) {
        _nowPlayingTvShows.value = tvShows
    }

    fun onAppBarStateChanged(state: AppBarStateChangeListener.State) {
        val titleRes = when (state) {
            COLLAPSED -> R.string.tv_show_message_top_rated_series
            EXPANDED, IDLE -> R.string.tv_show_message_tv_series
        }

        postToolbarTitle(getString(titleRes))
    }

    private fun postToolbarTitle(title: String) {
        _toolbarTitle.value = title
    }

    fun onTopRatedTvShowClick(tvShow: TvShowUiModel) {
        navigateTvShowDetailFragment(tvShow.id)
    }

    fun onNowPlayingShowClick(show: ShowUiModel) {
        navigateTvShowDetailFragment(show.id)
    }

    private fun navigateTvShowDetailFragment(id: Long) {
        navigate(TvShowFragmentDirections.toTvShowDetail(id))
    }
}
