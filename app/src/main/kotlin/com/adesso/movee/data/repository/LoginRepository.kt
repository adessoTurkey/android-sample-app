package com.adesso.movee.data.repository

import com.adesso.movee.data.local.datasource.LoginLocalDataSource
import com.adesso.movee.data.remote.datasource.LoginRemoteDataSource
import com.adesso.movee.data.remote.model.login.LoginRequestModel
import com.adesso.movee.data.remote.model.login.SessionRequestModel
import com.adesso.movee.domain.LoginUseCase
import com.adesso.movee.uimodel.LoginState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val localDataSource: LoginLocalDataSource,
    private val remoteDataSource: LoginRemoteDataSource
) {

    fun getLoginState() =
        if (localDataSource.getSessionToken()
            .isNullOrBlank()
        ) LoginState.LOGGED_OUT else LoginState.LOGGED_IN

    suspend fun login(loginParams: LoginUseCase.Params) {
        val requestTokenResponse = remoteDataSource.createRequestToken()

        val loginRequestTokenResponse =
            remoteDataSource.createRequestTokenWithLogin(
                LoginRequestModel(
                    username = loginParams.username,
                    password = loginParams.password,
                    requestToken = requestTokenResponse.requestToken
                )
            )

        val sessionResponse = remoteDataSource.createSession(
            SessionRequestModel(
                loginRequestTokenResponse.requestToken
            )
        )

        localDataSource.insertSessionToken(sessionResponse.sessionId)
    }
}
