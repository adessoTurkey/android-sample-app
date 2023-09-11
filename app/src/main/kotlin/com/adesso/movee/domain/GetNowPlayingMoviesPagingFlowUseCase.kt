package com.adesso.movee.domain

import androidx.paging.PagingData
import com.adesso.movee.data.repository.MovieRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.MovieUiModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetNowPlayingMoviesPagingFlowUseCase @Inject constructor(
    private val repository: MovieRepository
) : UseCase<Flow<PagingData<MovieUiModel>>, UseCase.None>() {

    override suspend fun buildUseCase(params: None) = repository.getNowPlayingMoviesPagingFlow()
}
