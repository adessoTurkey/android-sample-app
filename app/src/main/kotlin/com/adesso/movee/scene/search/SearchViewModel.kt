package com.adesso.movee.scene.search

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.MultiSearchUseCase
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.uimodel.MovieMultiSearchUiModel
import com.adesso.movee.uimodel.MultiSearchUiModel
import com.adesso.movee.uimodel.PersonMultiSearchUiModel
import com.adesso.movee.uimodel.TvShowMultiSearchUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val multiSearchUseCase: MultiSearchUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _multiSearchResults = mutableStateOf<List<MultiSearchUiModel>>(listOf())
    val multiSearchResults: State<List<MultiSearchUiModel>> = _multiSearchResults

    private val _shouldShowEmptyResultView = MutableLiveData(false)
    val shouldShowEmptyResultView: LiveData<Boolean> get() = _shouldShowEmptyResultView
    private var multiSearchJob: Job? = null

    fun onTextChange(text: String?) {
        val query = text?.trim() ?: return
        if (query.length > MIN_SEARCHABLE_LENGTH) {
            multiSearchJob?.cancel()
            multiSearchJob = bgScope.launch {
                val searchResult = multiSearchUseCase.run(MultiSearchUseCase.Params(query))
                onUIThread {
                    searchResult
                        .onSuccess(::postMultiSearchResult)
                        .onFailure(::handleSearchFailure)
                }
            }
        }
    }

    fun refreshData() {
        _multiSearchResults.value = emptyList()
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
    }
}
