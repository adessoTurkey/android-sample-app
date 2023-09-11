package com.adesso.movee.domain

import androidx.paging.PagingData
import com.adesso.movee.data.repository.TvShowRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.TvShowUiModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTopRatedTvShowsPagingFlowUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) : UseCase<Flow<PagingData<TvShowUiModel>>, UseCase.None>() {

    override suspend fun buildUseCase(params: None): Flow<PagingData<TvShowUiModel>> {
        return tvShowRepository.getTopRatedTvShowsPagingFlow()
    }
}
