package com.adesso.movee.data.repository

import com.adesso.movee.data.local.datasource.LoginLocalDataSource
import com.adesso.movee.data.remote.datasource.LoginRemoteDataSource
import com.adesso.movee.data.remote.model.login.LoginRequestModel
import com.adesso.movee.data.remote.model.login.LoginResponseModel
import com.adesso.movee.data.remote.model.login.RequestTokenResponseModel
import com.adesso.movee.data.remote.model.login.SessionRequestModel
import com.adesso.movee.data.remote.model.login.SessionResponseModel
import com.adesso.movee.domain.LoginUseCase
import com.adesso.movee.uimodel.LoginState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginRepositoryTest {

    private val localDataSource: LoginLocalDataSource = mock()
    private val remoteDataSource: LoginRemoteDataSource = mock()
    private val loginParams = LoginUseCase.Params("username", "password")

    @Test
    fun `when session token is null login state should return logged out`() {
        val repository = createRepository()

        whenever(localDataSource.getSessionToken()).thenReturn(null)

        val result = repository.getLoginState()

        assertEquals(LoginState.LOGGED_OUT, result)
    }

    @Test
    fun `when session token is blank login state should return logged out`() {
        val repository = createRepository()

        whenever(localDataSource.getSessionToken()).thenReturn("      ")

        val result = repository.getLoginState()

        assertEquals(LoginState.LOGGED_OUT, result)
    }

    @Test
    fun `when session token has value login state should return logged in`() {
        val repository = createRepository()

        whenever(localDataSource.getSessionToken()).thenReturn("token")

        val result = repository.getLoginState()

        assertEquals(LoginState.LOGGED_IN, result)
    }

    @Test
    fun `when login is successful should save token`() = runBlocking {
        val repository = createRepository()

        val tokenResponseModel = RequestTokenResponseModel(true, "token")
        val loginRequest = LoginRequestModel(
            loginParams.username,
            loginParams.password,
            tokenResponseModel.requestToken
        )
        val loginResult = LoginResponseModel(true, "token")
        val sessionRequest = SessionRequestModel("token")
        val sessionResponse = SessionResponseModel(true, "session_token")

        whenever(remoteDataSource.createRequestToken()).thenReturn(tokenResponseModel)
        whenever(remoteDataSource.createRequestTokenWithLogin(loginRequest)).thenReturn(loginResult)
        whenever(remoteDataSource.createSession(sessionRequest)).thenReturn(sessionResponse)

        repository.login(loginParams)

        verify(localDataSource).insertSessionToken(sessionResponse.sessionId)
    }

    private fun createRepository() = LoginRepository(localDataSource, remoteDataSource)
}
