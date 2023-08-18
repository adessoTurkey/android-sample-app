package com.adesso.movee.scene.moviedetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchMovieCreditsUseCase
import com.adesso.movee.domain.FetchMovieDetailUseCase
import com.adesso.movee.uimodel.MovieCastUiModel
import com.adesso.movee.uimodel.MovieCreditUiModel
import com.adesso.movee.uimodel.MovieDetailUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val fetchMovieDetailUseCase: FetchMovieDetailUseCase,
    private val fetchMovieCreditsUseCase: FetchMovieCreditsUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _movieDetails = MutableLiveData<MovieDetailUiModel>()
    private val _movieCredits = MutableLiveData<MovieCreditUiModel>()
    val movieDetails: LiveData<MovieDetailUiModel> get() = _movieDetails
    val movieCredits: LiveData<MovieCreditUiModel> get() = _movieCredits

    fun fetchMovieDetails(id: Long) {
        if (_movieDetails.value == null) {
            viewModelScope.launch {
                val movieDetailResult =
                    fetchMovieDetailUseCase.run(FetchMovieDetailUseCase.Params(id))

                runOnViewModelScope {
                    movieDetailResult
                        .onSuccess(::postMovieDetail)
                        .onFailure(::handleFailure)
                }
            }
        }
    }

    private fun postMovieDetail(movieDetailUiModel: MovieDetailUiModel) {
        _movieDetails.value = movieDetailUiModel
    }

    fun fetchMovieCredits(id: Long) {
        if (_movieCredits.value == null) {
            viewModelScope.launch {
                val movieCreditsResult =
                    fetchMovieCreditsUseCase.run(FetchMovieCreditsUseCase.Params(id))

                runOnViewModelScope {
                    movieCreditsResult
                        .onSuccess(::postMovieCredits)
                        .onFailure(::handleFailure)
                }
            }
        }
    }

    private fun postMovieCredits(movieCreditUiModel: MovieCreditUiModel) {
        _movieCredits.value = movieCreditUiModel
    }

    fun onMovieCastClick(movieCastViewModel: MovieCastUiModel) {
        navigate(MovieDetailFragmentDirections.toPersonDetail(movieCastViewModel.id))
    }
}
