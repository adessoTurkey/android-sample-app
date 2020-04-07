package com.adesso.movee.scene.login

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.LoginUseCase
import com.adesso.movee.internal.util.DispatcherProvider
import com.adesso.movee.internal.util.Event
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dispatchers: DispatcherProvider,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _navigateUri = MutableLiveData<Event<Uri>>()
    private val _loginInProgress = MutableLiveData<Boolean>()
    val navigateUri: LiveData<Event<Uri>> get() = _navigateUri
    val loginInProgress: LiveData<Boolean> get() = _loginInProgress

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun onForgotPasswordClick() {
        postNavigateUri(URL_FORGOT_PASSWORD)
    }

    fun onLoginClick() {
        uiScope.launch {
            val username = username.value?.takeIf { it.isNotBlank() } ?: return@launch
            val password = password.value?.takeIf { it.isNotBlank() } ?: return@launch

            _loginInProgress.value = true

            val loginResult = withContext(dispatchers.default) {
                loginUseCase.run(LoginUseCase.Params(username, password))
            }

            loginResult.either(::handleFailure, ::navigateHome)

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
        _navigateUri.value = Event(Uri.parse(url))
    }

    companion object {
        private const val URL_REGISTER = "https://www.themoviedb.org/account/signup"
        private const val URL_FORGOT_PASSWORD = "https://www.themoviedb.org/account/reset-password"
    }
}
