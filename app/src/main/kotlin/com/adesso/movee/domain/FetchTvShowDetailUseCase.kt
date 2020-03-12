package com.adesso.movee.domain

import com.adesso.movee.data.repository.TvShowRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.TvShowDetailUiModel
import javax.inject.Inject

class FetchTvShowDetailUseCase @Inject constructor(
    private val repository: TvShowRepository
) : UseCase<TvShowDetailUiModel, FetchTvShowDetailUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) = repository.fetchTvShowDetail(params.id)

    data class Params(val id: Long)
}