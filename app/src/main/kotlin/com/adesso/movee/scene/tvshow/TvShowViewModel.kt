package com.adesso.movee.scene.tvshow

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.adesso.movee.R
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchNowPlayingTvShowsUseCase
import com.adesso.movee.domain.FetchTopRatedTvShowsUseCase
import com.adesso.movee.internal.util.AppBarStateChangeListener
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.COLLAPSED
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.EXPANDED
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.IDLE
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.ShowHeaderUiModel
import com.adesso.movee.uimodel.ShowUiModel
import com.adesso.movee.uimodel.TvShowUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val fetchTopRatedTvShowsUseCase: FetchTopRatedTvShowsUseCase,
    private val fetchNowPlayingTvShowsUseCase: FetchNowPlayingTvShowsUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _topRatedTvShows = MutableStateFlow<List<TvShowUiModel>?>(null)
    private val _toolbarTitle = MutableStateFlow<String?>(null)
    private val _toolbarSubtitle = MutableStateFlow(getString(R.string.tv_show_message_top_rated))
    private val _nowPlayingTvShows = MutableStateFlow<List<TvShowUiModel>?>(null)
    val topRatedTvShows: StateFlow<List<TvShowUiModel>?> get() = _topRatedTvShows
    val showHeader = combine(
        _toolbarTitle,
        _toolbarSubtitle,
        _nowPlayingTvShows
    ) { title, subtitle, nowPlayingTvShows ->
        ShowHeaderUiModel(
            title = title,
            subtitle = subtitle,
            nowPlayingShows = nowPlayingTvShows
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        fetchTopRatedTvShows()
        fetchNowPlayingTvShows()
    }

    private fun fetchTopRatedTvShows() {
        viewModelScope.launch {
            val topRatedTvShowsResult = fetchTopRatedTvShowsUseCase.run(UseCase.None)

            runOnViewModelScope {
                topRatedTvShowsResult
                    .onSuccess(::postTopRatedTvShows)
                    .onFailure(::handleFailure)
            }
        }
    }

    private fun postTopRatedTvShows(tvShows: List<TvShowUiModel>) {
        _topRatedTvShows.value = tvShows
    }

    private fun fetchNowPlayingTvShows() {
        viewModelScope.launch {
            val nowPlayingTvShowsResult = fetchNowPlayingTvShowsUseCase.run(UseCase.None)

            runOnViewModelScope {
                nowPlayingTvShowsResult
                    .onSuccess(::postNowPlayingTvShows)
                    .onFailure(::handleFailure)
            }
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
