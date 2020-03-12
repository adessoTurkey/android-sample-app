package com.example.movee.domain

import com.example.movee.data.repository.TvShowRepository
import com.example.movee.internal.util.UseCase
import com.example.movee.uimodel.TvShowDetailUiModel
import javax.inject.Inject

class FetchTvShowDetailUseCase @Inject constructor(
    private val repository: TvShowRepository
) : UseCase<TvShowDetailUiModel, FetchTvShowDetailUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) = repository.fetchTvShowDetail(params.id)

    data class Params(val id: Long)
}