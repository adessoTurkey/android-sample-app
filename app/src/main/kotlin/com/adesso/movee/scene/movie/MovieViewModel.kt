package com.adesso.movee.scene.movie

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.adesso.movee.R
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchNowPlayingMoviesUseCase
import com.adesso.movee.domain.GetPopularMoviesPagingFlowUseCase
import com.adesso.movee.domain.ShouldRefreshPagingUseCase
import com.adesso.movee.internal.util.AppBarStateChangeListener
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.COLLAPSED
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.EXPANDED
import com.adesso.movee.internal.util.AppBarStateChangeListener.State.IDLE
import com.adesso.movee.internal.util.TripleCombinedLiveData
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.MovieUiModel
import com.adesso.movee.uimodel.ShowHeaderUiModel
import com.adesso.movee.uimodel.ShowUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMoviesPagingFlowUseCase: GetPopularMoviesPagingFlowUseCase,
    private val fetchNowPlayingMoviesUseCase: FetchNowPlayingMoviesUseCase,
    private val shouldRefreshPagingUseCase: ShouldRefreshPagingUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _popularMovies = MutableStateFlow<PagingData<MovieUiModel>>(PagingData.empty())
    private val _toolbarTitle = MutableLiveData<String>()
    private val _toolbarSubtitle = MutableLiveData(getString(R.string.movie_message_popular))
    private val _nowPlayingMovies = MutableLiveData<List<MovieUiModel>>()
    val popularMovies = _popularMovies.asStateFlow()
    val showHeader = TripleCombinedLiveData(
        _toolbarTitle,
        _toolbarSubtitle,
        _nowPlayingMovies
    ) { title, subtitle, nowPlayingShows ->
        ShowHeaderUiModel(
            title,
            subtitle,
            nowPlayingShows
        )
    }

    private val _shouldRefreshPaging = MutableStateFlow(false)
    val shouldRefreshPaging: StateFlow<Boolean>
        get() {
            viewModelScope.launch {
                shouldRefreshPagingUseCase.execute().collectLatest {
                    Timber.d("SHOULD REFRESH: $it")
                    _shouldRefreshPaging.value = it
                }
            }

            return _shouldRefreshPaging.asStateFlow()
        }

    init {
        fetchPopularMovies()
        fetchNowPlayingMovies()
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            val nowPlayingMoviesResult = fetchNowPlayingMoviesUseCase.run(UseCase.None)

            runOnViewModelScope {
                nowPlayingMoviesResult
                    .onSuccess(::postNowPlayingMovieList)
                    .onFailure(::handleFailure)
            }
        }
    }

    private fun postNowPlayingMovieList(movies: List<MovieUiModel>) {
        _nowPlayingMovies.value = movies
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            val popularMoviesResult = getPopularMoviesPagingFlowUseCase.run(UseCase.None)

            runOnViewModelScope {
                popularMoviesResult
                    .onSuccess(::postPopularMoviesPagedData)
                    .onFailure(::handleFailure)
            }
        }
    }

    private fun postPopularMoviesPagedData(pagingFlow: Flow<PagingData<MovieUiModel>>) {
        viewModelScope.launch {
            pagingFlow.cachedIn(viewModelScope).collect {
                _popularMovies.value = it
            }
        }
    }

    private fun postToolbarTitle(@StringRes titleRes: Int) {
        _toolbarTitle.value = getString(titleRes)
    }

    fun appbarStateChanged(state: AppBarStateChangeListener.State) {
        when (state) {
            COLLAPSED -> postToolbarTitle(R.string.movie_message_popular_movies)
            EXPANDED, IDLE -> postToolbarTitle(R.string.movie_message_movies)
        }
    }

    fun onPopularMovieClick(movie: MovieUiModel) {
        navigateMovieDetailFragment(movie)
    }

    fun onNowPlayingShowClick(show: ShowUiModel) {
        navigateMovieDetailFragment(show)
    }

    private fun navigateMovieDetailFragment(show: ShowUiModel) {
        navigate(MovieFragmentDirections.toMovieDetail(show.id))
    }
}
