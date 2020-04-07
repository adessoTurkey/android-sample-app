package com.adesso.movee.scene.login

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adesso.movee.domain.LoginUseCase
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.functional.Either
import com.adesso.movee.navigation.NavigationCommand
import com.adesso.movee.util.MainCoroutineScopeRule
import com.adesso.movee.util.TestDispatcherProvider
import com.adesso.movee.util.captureValues
import com.adesso.movee.util.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutinesRule = MainCoroutineScopeRule()

    private val loginUseCase: LoginUseCase = mock()
    private val testDispatcherProvider = TestDispatcherProvider()
    private val application = ApplicationProvider.getApplicationContext<Application>()

    private val username = "username"
    private val password = "password"

    @Test
    fun `when username is invalid should not call login use case`() {
        val viewModel = createViewModel()

        viewModel.username.value = ""
        viewModel.password.value = password

        viewModel.onLoginClick()

        verifyBlocking(loginUseCase, never()) { run(LoginUseCase.Params("", password)) }
    }

    @Test
    fun `when password is invalid should not call login use case`() {
        val viewModel = createViewModel()

        viewModel.username.value = username
        viewModel.password.value = ""

        viewModel.onLoginClick()

        verifyBlocking(loginUseCase, never()) { run(LoginUseCase.Params(username, "")) }
    }

    @Test
    fun `when login credentials are valid should update login in progress`() = runBlocking {
        val viewModel = createViewModel()

        viewModel.username.value = username
        viewModel.password.value = password

        whenever(loginUseCase.run(LoginUseCase.Params(username, password)))
            .thenReturn(Either.Right(Unit))

        val expected = listOf<Boolean?>(true, false)

        viewModel.loginInProgress.captureValues {
            viewModel.onLoginClick()
            assertThat(values, `is`(expected))
        }
    }

    @Test
    fun `when login succeed should navigate home`() = runBlocking {
        val viewModel = createViewModel()

        viewModel.username.value = username
        viewModel.password.value = password

        whenever(loginUseCase.run(LoginUseCase.Params(username, password)))
            .thenReturn(Either.Right(Unit))

        viewModel.onLoginClick()

        val result = viewModel.navigation.getValueForTest()?.getContentIfNotHandled()

        assertEquals(NavigationCommand.ToDirection(LoginFragmentDirections.toHome()), result)
    }

    @Test
    fun `when login credential are valid and login failed should show failure`() = runBlocking {
        val viewModel = createViewModel()

        viewModel.username.value = username
        viewModel.password.value = password

        val failure = Failure.EmptyResponse

        whenever(loginUseCase.run(LoginUseCase.Params(username, password)))
            .thenReturn(Either.Left(failure))

        viewModel.onLoginClick()

        assertNotNull(viewModel.failurePopup.getValueForTest()?.getContentIfNotHandled())
    }

    private fun createViewModel() = LoginViewModel(loginUseCase, testDispatcherProvider, application)
}
