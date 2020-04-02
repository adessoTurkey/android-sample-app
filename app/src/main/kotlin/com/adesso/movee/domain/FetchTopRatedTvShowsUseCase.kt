package com.adesso.movee.domain

import com.adesso.movee.data.repository.TvShowRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.TvShowUiModel
import javax.inject.Inject

class FetchTopRatedTvShowsUseCase @Inject constructor(
    private val repository: TvShowRepository
) : UseCase<List<TvShowUiModel>, UseCase.None>() {

    override suspend fun buildUseCase(params: None) = repository.fetchTopRatedTvShows()
}
