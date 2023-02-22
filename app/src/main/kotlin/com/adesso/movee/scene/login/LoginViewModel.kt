package com.adesso.movee.scene.login

import android.app.Application
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.LoginUseCase
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _navigateUri = Channel<Uri>(Channel.CONFLATED)
    val navigateUri = _navigateUri.receiveAsFlow()

    private val _loginInProgress = MutableStateFlow<Boolean>(false)
    val loginInProgress: StateFlow<Boolean> get() = _loginInProgress

    val username = MutableStateFlow<String?>(null)
    val password = MutableStateFlow<String?>(null)

    fun onForgotPasswordClick() {
        postNavigateUri(URL_FORGOT_PASSWORD)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            val username = username.value ?: return@launch
            val password = password.value ?: return@launch

            _loginInProgress.value = true

            val loginResult = runOnViewModelScope {
                loginUseCase.run(LoginUseCase.Params(username, password))
            }

            loginResult
                .onSuccess { navigateHome() }
                .onFailure(::handleFailure)

            _loginInProgress.value = false
        }
    }

    private fun navigateHome() {
        navigate(LoginFragmentDirections.toHome())
    }

    fun onRegisterClick() {
        postNavigateUri(URL_REGISTER)
    }

    private fun postNavigateUri(url: String) {
        _navigateUri.trySend(Uri.parse(url))
    }

    companion object {
        private const val URL_REGISTER = "https://www.themoviedb.org/account/signup"
        private const val URL_FORGOT_PASSWORD = "https://www.themoviedb.org/account/reset-password"
    }
}
