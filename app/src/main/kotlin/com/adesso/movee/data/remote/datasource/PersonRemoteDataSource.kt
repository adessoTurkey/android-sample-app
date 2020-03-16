package com.adesso.movee.data.remote.datasource

import com.adesso.movee.data.remote.BaseRemoteDataSource
import com.adesso.movee.data.remote.api.PersonService
import com.adesso.movee.data.remote.model.person.PersonDetailResponseModel
import javax.inject.Inject

class PersonRemoteDataSource @Inject constructor(
    private val service: PersonService
) : BaseRemoteDataSource() {

    suspend fun fetchPersonDetail(id: Long): PersonDetailResponseModel = invoke {
        service.fetchPersonDetail(id)
    }
}