package com.adesso.movee.domain

import com.adesso.movee.data.repository.TvShowRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.TvShowCreditUiModel
import javax.inject.Inject

class FetchTvShowCreditsUseCase @Inject constructor(
    private val repository: TvShowRepository
) : UseCase<TvShowCreditUiModel, FetchTvShowCreditsUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) = repository.fetchCredits(params.id)

    data class Params(val id: Long)
}