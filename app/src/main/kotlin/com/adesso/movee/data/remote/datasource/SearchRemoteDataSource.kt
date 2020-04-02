package com.adesso.movee.data.remote.datasource

import com.adesso.movee.data.remote.BaseRemoteDataSource
import com.adesso.movee.data.remote.api.SearchService
import com.adesso.movee.data.remote.model.search.MultiSearchResponseModel
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val service: SearchService
) : BaseRemoteDataSource() {

    suspend fun multiSearch(query: String): MultiSearchResponseModel = invoke {
        service.multiSearch(query)
    }
}
