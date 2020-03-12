package com.example.movee.domain

import com.example.movee.data.repository.MovieRepository
import com.example.movee.internal.util.UseCase
import com.example.movee.uimodel.MovieUiModel
import javax.inject.Inject

class FetchPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) : UseCase<List<MovieUiModel>, UseCase.None>() {

    override suspend fun buildUseCase(params: None) = repository.fetchPopularMovies()
}