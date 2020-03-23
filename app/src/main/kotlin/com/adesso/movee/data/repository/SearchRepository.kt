package com.adesso.movee.data.repository

import com.adesso.movee.data.remote.datasource.SearchRemoteDataSource
import com.adesso.movee.uimodel.MultiSearchUiModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource
) {

    suspend fun multiSearch(query: String): List<MultiSearchUiModel> {
        return remoteDataSource.multiSearch(query)
            .multiSearchResults
            .map { it.toUiModel() }
    }
}