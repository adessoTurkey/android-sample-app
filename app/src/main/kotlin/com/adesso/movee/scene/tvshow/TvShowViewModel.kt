package com.adesso.movee.scene.tvshow

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.adesso.movee.R
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.GetNowPlayingTvShowsPagingFlowUseCase
import com.adesso.movee.domain.GetTopRatedTvShowsPagingFlowUseCase
import com.adesso.movee.domain.ShouldRefreshPagingUseCase
import com.adesso.movee.internal.util.AppBarStateChangeListener
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.COLLAPSED
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.EXPANDED
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.IDLE
import com.adesso.movee.internal.util.TripleCombinedLiveData
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.ShowHeaderUiModel
import com.adesso.movee.uimodel.ShowUiModel
import com.adesso.movee.uimodel.TvShowUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val shouldRefreshPagingUseCase: ShouldRefreshPagingUseCase,
    private val getNowPlayingTvShowsPagingFlowUseCase: GetNowPlayingTvShowsPagingFlowUseCase,
    private val getTopRatedTvShowsPagingFlowUseCase: GetTopRatedTvShowsPagingFlowUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _topRatedTvShows = MutableStateFlow<PagingData<TvShowUiModel>>(PagingData.empty())
    private val _toolbarTitle = MutableLiveData<String>()
    private val _toolbarSubtitle = MutableLiveData(getString(R.string.tv_show_message_top_rated))
    private val _nowPlayingTvShows = MutableLiveData<PagingData<TvShowUiModel>>(PagingData.empty())
    val topRatedTvShows = _topRatedTvShows.asStateFlow()

    @Suppress("UNCHECKED_CAST")
    val showHeader = TripleCombinedLiveData(
        _toolbarTitle,
        _toolbarSubtitle,
        _nowPlayingTvShows
    ) { title, subtitle, nowPlayingShows ->
        ShowHeaderUiModel(
            title,
            subtitle,
            nowPlayingShows as PagingData<ShowUiModel>?
        )
    }

    val shouldRefreshPaging = shouldRefreshPagingUseCase.execute()

    init {
        fetchTopRatedTvShows()
        fetchNowPlayingTvShows()
    }

    private fun fetchTopRatedTvShows() {
        viewModelScope.launch {
            val topRatedTvShowsResult = getTopRatedTvShowsPagingFlowUseCase.run(UseCase.None)

            runOnViewModelScope {
                topRatedTvShowsResult
                    .onSuccess(::postTopRatedTvShows)
                    .onFailure(::handleFailure)
            }
        }
    }

    private fun fetchNowPlayingTvShows() {
        viewModelScope.launch {
            val nowPlayingTvShowsResult = getNowPlayingTvShowsPagingFlowUseCase.run(UseCase.None)

            runOnViewModelScope {
                nowPlayingTvShowsResult
                    .onSuccess(::postNowPlayingTvShows)
                    .onFailure(::handleFailure)
            }
        }
    }

    private fun postTopRatedTvShows(pagingFlow: Flow<PagingData<TvShowUiModel>>) {
        viewModelScope.launch {
            pagingFlow.cachedIn(viewModelScope).collect {
                _topRatedTvShows.value = it
            }
        }
    }

    private fun postNowPlayingTvShows(pagingFlow: Flow<PagingData<TvShowUiModel>>) {
        viewModelScope.launch {
            pagingFlow.cachedIn(viewModelScope).collect {
                _nowPlayingTvShows.value = it
            }
        }
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
