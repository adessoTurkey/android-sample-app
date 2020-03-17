package com.adesso.movee.scene.tvshowdetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchTvShowCreditsUseCase
import com.adesso.movee.domain.FetchTvShowDetailUseCase
import com.adesso.movee.uimodel.TvShowDetailUiModel
import com.adesso.movee.uimodel.TvShowCastUiModel
import com.adesso.movee.uimodel.TvShowCreditUiModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvShowDetailViewModel @Inject constructor(
    private val fetchTvShowDetailUseCase: FetchTvShowDetailUseCase,
    private val fetchTvShowCreditsUseCase: FetchTvShowCreditsUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _tvShowDetails = MutableLiveData<TvShowDetailUiModel>()
    val tvShowDetails: LiveData<TvShowDetailUiModel> get() = _tvShowDetails
    private val _tvShowCredits = MutableLiveData<TvShowCreditUiModel>()
    val tvShowCasts: LiveData<List<TvShowCastUiModel>> =
        Transformations.map(_tvShowCredits) { tvShowCredits ->
            tvShowCredits.cast
        }

    fun fetchTvShowDetail(id: Long) {
        if (_tvShowDetails.value == null) {
            bgScope.launch {
                val tvShowDetailResult =
                    fetchTvShowDetailUseCase.run(FetchTvShowDetailUseCase.Params(id))

                onUIThread {
                    tvShowDetailResult.either(::handleFailure, ::postTvShowDetail)
                }
            }
        }
    }

    private fun postTvShowDetail(tvShowDetail: TvShowDetailUiModel) {
        _tvShowDetails.value = tvShowDetail
    }

    fun fetchTvShowCredits(id: Long) {
        if (_tvShowCredits.value == null) {
            bgScope.launch {
                val tvShowCreditResult =
                    fetchTvShowCreditsUseCase.run(FetchTvShowCreditsUseCase.Params(id))

                onUIThread {
                    tvShowCreditResult.either(::handleFailure, ::postTvShowCredits)
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
