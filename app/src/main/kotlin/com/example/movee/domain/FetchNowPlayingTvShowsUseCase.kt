package com.example.movee.domain

import com.example.movee.data.repository.TvShowRepository
import com.example.movee.internal.util.UseCase
import com.example.movee.uimodel.TvShowUiModel
import javax.inject.Inject

class FetchNowPlayingTvShowsUseCase @Inject constructor(
    private val repository: TvShowRepository
) : UseCase<List<TvShowUiModel>, UseCase.None>() {

    override suspend fun buildUseCase(params: None) = repository.fetchNowPlayingTvShows()
}