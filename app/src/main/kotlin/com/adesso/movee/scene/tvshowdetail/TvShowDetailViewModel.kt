package com.adesso.movee.scene.tvshowdetail

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchTvShowCreditsUseCase
import com.adesso.movee.domain.FetchTvShowDetailUseCase
import com.adesso.movee.uimodel.TvShowCastUiModel
import com.adesso.movee.uimodel.TvShowCreditUiModel
import com.adesso.movee.uimodel.TvShowDetailUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val fetchTvShowDetailUseCase: FetchTvShowDetailUseCase,
    private val fetchTvShowCreditsUseCase: FetchTvShowCreditsUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _tvShowDetails = MutableStateFlow<TvShowDetailUiModel?>(null)
    val tvShowDetails: StateFlow<TvShowDetailUiModel?> get() = _tvShowDetails
    private val _tvShowCredits = MutableStateFlow<TvShowCreditUiModel?>(null)
    val tvShowCasts: StateFlow<List<TvShowCastUiModel>?> =
        _tvShowCredits.map { it?.cast }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun fetchTvShowDetail(id: Long) {
        if (_tvShowDetails.value == null) {
            viewModelScope.launch {
                val tvShowDetailResult =
                    fetchTvShowDetailUseCase.run(FetchTvShowDetailUseCase.Params(id))

                runOnViewModelScope {
                    tvShowDetailResult
                        .onSuccess(::postTvShowDetail)
                        .onFailure(::handleFailure)
                }
            }
        }
    }

    private fun postTvShowDetail(tvShowDetail: TvShowDetailUiModel) {
        _tvShowDetails.value = tvShowDetail
    }

    fun fetchTvShowCredits(id: Long) {
        if (_tvShowCredits.value == null) {
            viewModelScope.launch {
                val tvShowCreditResult =
                    fetchTvShowCreditsUseCase.run(FetchTvShowCreditsUseCase.Params(id))

                runOnViewModelScope {
                    tvShowCreditResult
                        .onSuccess(::postTvShowCredits)
                        .onFailure(::handleFailure)
                }
            }
        }
    }

    private fun postTvShowCredits(tvShowCreditUiModel: TvShowCreditUiModel) {
        _tvShowCredits.value = tvShowCreditUiModel
    }

    fun onTvShowCastClick(tvShowCast: TvShowCastUiModel) {
        navigate(TvShowDetailFragmentDirections.toPersonDetail(tvShowCast.id))
    }
}
