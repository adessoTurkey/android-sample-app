package com.adesso.movee.scene.search

import android.app.Application
import androidx.compose.runtime.MutableState
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
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val multiSearchUseCase: MultiSearchUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _results: MutableState<List<MultiSearchUiModel>> = mutableStateOf(listOf())
    val results: State<List<MultiSearchUiModel>> = _results
    val query = mutableStateOf("")
    val isResultLoaded = mutableStateOf(false)
    private var multiSearchJob: Job? = null
    val searchDebounce = DURATION_MS_INPUT_TIMEOUT

    init {
        onTextChange(query.value)
    }
    fun onTextChange(text: String?) {
        val query = text?.trim() ?: return
        if (query.length > MIN_SEARCHABLE_LENGTH) {
            multiSearchJob?.cancel()
            multiSearchJob = bgScope.launch {
                val searchResult = multiSearchUseCase.run(MultiSearchUseCase.Params(query))
                onUIThread {
                    searchResult.either(::handleSearchFailure, ::postMultiSearchResult)
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
        _results.value = multiSearchUiModels
        isResultLoaded.value = multiSearchUiModels.isEmpty()
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

    fun onQueryChanged(query: String){
        this.query.value = query
        
    }

    companion object {
        private const val MIN_SEARCHABLE_LENGTH = 2
        private const val DURATION_MS_INPUT_TIMEOUT = 250L
    }
}
