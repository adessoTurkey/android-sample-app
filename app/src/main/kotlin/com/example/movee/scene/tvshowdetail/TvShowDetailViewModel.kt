package com.example.movee.scene.tvshowdetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movee.base.BaseAndroidViewModel
import com.example.movee.domain.FetchTvShowDetailUseCase
import com.example.movee.uimodel.ShowUiModel
import com.example.movee.uimodel.TvShowDetailUiModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvShowDetailViewModel @Inject constructor(
    private val fetchTvShowDetailUseCase: FetchTvShowDetailUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _tvShowDetails = MutableLiveData<TvShowDetailUiModel>()
    val tvShowDetails: LiveData<TvShowDetailUiModel> get() = _tvShowDetails

    fun fetchTvShowDetail(show: ShowUiModel) {
        if (_tvShowDetails.value == null) {
            bgScope.launch {
                val tvShowDetailResult =
                    fetchTvShowDetailUseCase.run(FetchTvShowDetailUseCase.Params(show.id))

                onUIThread {
                    tvShowDetailResult.either(::handleFailure, ::postTvShowDetail)
                }
            }
        }
    }

    private fun postTvShowDetail(tvShowDetail: TvShowDetailUiModel) {
        _tvShowDetails.value = tvShowDetail
    }
}
