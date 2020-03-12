package com.example.movee.domain

import com.example.movee.data.repository.MovieRepository
import com.example.movee.internal.util.UseCase
import com.example.movee.uimodel.MovieDetailUiModel
import javax.inject.Inject

class FetchMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) : UseCase<MovieDetailUiModel, FetchMovieDetailUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) = repository.fetchMovieDetail(params.id)

    data class Params(val id: Long)
}