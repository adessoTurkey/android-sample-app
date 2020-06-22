package com.adesso.movee.scene.moviedetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchMovieCreditsUseCase
import com.adesso.movee.domain.FetchMovieDetailUseCase
import com.adesso.movee.uimodel.MovieCreditUiModel
import com.adesso.movee.uimodel.MovieDetailUiModel
import javax.inject.Inject
import kotlinx.coroutines.launch

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
            bgScope.launch {
                val movieDetailResult =
                    fetchMovieDetailUseCase.run(FetchMovieDetailUseCase.Params(id))

                onUIThread {
                    movieDetailResult.either(::handleFailure, ::postMovieDetail)
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
                movieCreditsResult.either(::handleFailure, ::postMovieCredits)
            }
        }
    }

    private fun postMovieCredits(movieCreditUiModel: MovieCreditUiModel) {
        _movieCredits.value = movieCreditUiModel
    }
}
