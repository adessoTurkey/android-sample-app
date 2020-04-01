package com.adesso.movee.data.remote.datasource

import com.adesso.movee.data.remote.BaseRemoteDataSource
import com.adesso.movee.data.remote.api.UserService
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val service: UserService
) : BaseRemoteDataSource() {

    suspend fun fetchUserDetails() = invoke {
        service.fetchUserDetails()
    }
}