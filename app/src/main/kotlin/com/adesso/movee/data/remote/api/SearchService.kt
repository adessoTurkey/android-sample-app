package com.adesso.movee.data.remote.api

import com.adesso.movee.data.remote.model.search.MultiSearchResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET(MULTI_SEARCH)
    suspend fun multiSearch(@Query("query") query: String): MultiSearchResponseModel

    companion object {
        const val MULTI_SEARCH = "search/multi"
    }
}