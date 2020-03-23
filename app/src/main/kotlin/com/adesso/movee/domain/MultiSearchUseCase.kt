package com.adesso.movee.domain

import com.adesso.movee.data.repository.SearchRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.MultiSearchUiModel
import javax.inject.Inject

class MultiSearchUseCase @Inject constructor(
    private val repository: SearchRepository
) : UseCase<List<MultiSearchUiModel>, MultiSearchUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) = repository.multiSearch(params.query)

    data class Params(val query: String)
}