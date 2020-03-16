package com.adesso.movee.scene.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.MultiSearchUseCase
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.uimodel.MovieMultiSearchUiModel
import com.adesso.movee.uimodel.MultiSearchUiModel
import com.adesso.movee.uimodel.PersonMultiSearchUiModel
import com.adesso.movee.uimodel.TvShowMultiSearchUiModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val multiSearchUseCase: MultiSearchUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val multiSearchQueryChannel = Channel<String>(capacity = Channel.CONFLATED)
    private val _multiSearchResults = MutableLiveData<List<MultiSearchUiModel>>()
    private val _shouldShowEmptyResultView = MutableLiveData<Boolean>()
    val multiSearchResults: LiveData<List<MultiSearchUiModel>> = _multiSearchResults
    val shouldShowEmptyResultView: LiveData<Boolean> get() = _shouldShowEmptyResultView

    init {
        consumeMultiSearchQueryChannel()
    }

    private fun consumeMultiSearchQueryChannel() {
        bgScope.launch {
            multiSearchQueryChannel
                .consumeAsFlow()
                .map { it.trim() }
                .filter { it.length > MIN_SEARCHABLE_LENGTH }
                .debounce(DURATION_MS_INPUT_TIMEOUT)
                .mapLatest {
                    multiSearchUseCase.run(MultiSearchUseCase.Params(it))
                }
                .collect {
                    onUIThread {
                        it.either(::handleSearchFailure, ::postMultiSearchResult)
                    }
                }
        }
    }

    private fun handleSearchFailure(failure: Failure) {
        if (failure is Failure.UnknownError && failure.exception is CancellationException) {
            return
        }

        handleFailure(failure)
    }

    private fun postMultiSearchResult(multiSearchUiModels: List<MultiSearchUiModel>) {
        _multiSearchResults.value = multiSearchUiModels
        _shouldShowEmptyResultView.value = multiSearchUiModels.isEmpty()
    }

    fun onQueryChange(search: String?) {
        search?.let {
            uiScope.launch {
                multiSearchQueryChannel.send(it)
            }
        }
    }

    fun onMultiSearchClick(multiSearch: MultiSearchUiModel) {
        navigateToSearchItem(multiSearch)
    }

    private fun navigateToSearchItem(multiSearch: MultiSearchUiModel) {
        val direction = when (multiSearch) {
            is MovieMultiSearchUiModel -> SearchFragmentDirections.toMovieDetail(multiSearch.id)
            is TvShowMultiSearchUiModel -> SearchFragmentDirections.toTvShowDetail(multiSearch.id)
            is PersonMultiSearchUiModel -> SearchFragmentDirections.toPersonDetail(multiSearch.id)
        }
        navigate(direction)
    }

    companion object {
        private const val MIN_SEARCHABLE_LENGTH = 2
        private const val DURATION_MS_INPUT_TIMEOUT = 250L
    }
}
