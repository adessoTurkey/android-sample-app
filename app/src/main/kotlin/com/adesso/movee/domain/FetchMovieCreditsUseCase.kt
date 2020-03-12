package com.adesso.movee.domain

import com.adesso.movee.data.repository.MovieRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.MovieCreditUiModel
import javax.inject.Inject

class FetchMovieCreditsUseCase @Inject constructor(
    private val repository: MovieRepository
) : UseCase<MovieCreditUiModel, FetchMovieCreditsUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) = repository.fetchCredits(params.id)

    data class Params(val id: Long)
}