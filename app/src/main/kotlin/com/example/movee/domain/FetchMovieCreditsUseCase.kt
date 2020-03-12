package com.example.movee.domain

import com.example.movee.data.repository.MovieRepository
import com.example.movee.internal.util.UseCase
import com.example.movee.uimodel.MovieCreditUiModel
import javax.inject.Inject

class FetchMovieCreditsUseCase @Inject constructor(
    private val repository: MovieRepository
) : UseCase<MovieCreditUiModel, FetchMovieCreditsUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) = repository.fetchCredits(params.id)

    data class Params(val id: Long)
}