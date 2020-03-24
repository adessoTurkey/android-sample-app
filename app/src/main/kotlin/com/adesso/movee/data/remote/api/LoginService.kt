package com.adesso.movee.data.remote.api

import com.adesso.movee.data.remote.model.login.LoginRequestModel
import com.adesso.movee.data.remote.model.login.LoginResponseModel
import com.adesso.movee.data.remote.model.login.RequestTokenResponseModel
import com.adesso.movee.data.remote.model.login.SessionRequestModel
import com.adesso.movee.data.remote.model.login.SessionResponseModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {

    @GET(REQUEST_TOKEN)
    suspend fun createRequestToken(): RequestTokenResponseModel

    @POST(LOGIN)
    suspend fun createRequestTokenWithLogin(@Body requestModel: LoginRequestModel): LoginResponseModel

    @POST(SESSION)
    suspend fun createSession(@Body requestModel: SessionRequestModel): SessionResponseModel

    companion object {
        const val REQUEST_TOKEN = "authentication/token/new"
        const val LOGIN = "authentication/token/validate_with_login"
        const val SESSION = "authentication/session/new"
    }
}