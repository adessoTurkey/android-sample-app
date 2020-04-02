package com.adesso.movee.data.remote.datasource

import com.adesso.movee.data.remote.BaseRemoteDataSource
import com.adesso.movee.data.remote.api.LoginService
import com.adesso.movee.data.remote.model.login.LoginRequestModel
import com.adesso.movee.data.remote.model.login.SessionRequestModel
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val service: LoginService
) : BaseRemoteDataSource() {

    suspend fun createRequestToken() = invoke {
        service.createRequestToken()
    }

    suspend fun createRequestTokenWithLogin(requestModel: LoginRequestModel) = invoke {
        service.createRequestTokenWithLogin(requestModel)
    }

    suspend fun createSession(requestModel: SessionRequestModel) = invoke {
        service.createSession(requestModel)
    }
}
