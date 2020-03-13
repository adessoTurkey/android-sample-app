package com.adesso.movee.domain

import com.adesso.movee.data.repository.PersonRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.PersonDetailUiModel
import javax.inject.Inject

class FetchPersonDetailsUseCase @Inject constructor(
    private val repository: PersonRepository
) : UseCase<PersonDetailUiModel, FetchPersonDetailsUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) = repository.fetchPersonDetail(params.id)

    data class Params(val id: Long)
}