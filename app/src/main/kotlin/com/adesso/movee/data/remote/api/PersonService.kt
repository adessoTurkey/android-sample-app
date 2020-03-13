package com.adesso.movee.data.remote.api

import com.adesso.movee.data.remote.model.person.PersonDetailResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonService {

    @GET(DETAIL)
    suspend fun fetchPersonDetail(@Path(PATH_PERSON_ID) id: Long): PersonDetailResponseModel

    companion object {
        const val PATH_PERSON_ID = "person_id"
        const val DETAIL = "person/{$PATH_PERSON_ID}"
    }
}