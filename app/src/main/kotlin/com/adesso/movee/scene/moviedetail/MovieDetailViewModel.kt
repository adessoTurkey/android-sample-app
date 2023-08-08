package com.adesso.movee.scene.moviedetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchMovieCreditsUseCase
import com.adesso.movee.domain.FetchMovieDetailUseCase
import com.adesso.movee.uimodel.MovieCastUiModel
import com.adesso.movee.uimodel.MovieCreditUiModel
import com.adesso.movee.uimodel.MovieDetailUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            bgScope.launch {
                val movieCreditsResult =
                    fetchMovieCreditsUseCase.run(FetchMovieCreditsUseCase.Params(id))

                onUIThread {
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
