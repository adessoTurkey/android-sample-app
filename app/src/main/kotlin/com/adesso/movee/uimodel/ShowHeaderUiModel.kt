package com.adesso.movee.uimodel

import androidx.paging.PagingData

class ShowHeaderUiModel(
    val title: String?,
    val subtitle: String?,
    val nowPlayingShows: PagingData<ShowUiModel>?
)
