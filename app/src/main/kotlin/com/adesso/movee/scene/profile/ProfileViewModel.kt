package com.adesso.movee.scene.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchUserDetailsUseCase
import com.adesso.movee.domain.GetLoginStateUseCase
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.LoginState
import com.adesso.movee.uimodel.UserDetailUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchUserDetailsUseCase: FetchUserDetailsUseCase,
    private val getLoginStateUseCase: GetLoginStateUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _userDetails = MutableLiveData<UserDetailUiModel?>()
    val userDetails: LiveData<UserDetailUiModel?> get() = _userDetails
    private val _loginState = MutableLiveData<LoginState>()
    val shouldShowUserDetails: LiveData<Boolean> = Transformations.map(_userDetails) { it != null }
    val shouldShowLoginView: LiveData<Boolean> = Transformations.map(_loginState) { loginState ->
        loginState == LoginState.LOGGED_IN
    }

    init {
        getLoginState()
    }

    private fun getLoginState() {
        viewModelScope.launch {
            val loginStateResult = getLoginStateUseCase.run(UseCase.None)

            runOnViewModelScope {
                loginStateResult
                    .onSuccess(::handleLoginStateSuccess)
                    .onFailure(::handleFailure)
            }
        }
    }

    private fun handleLoginStateSuccess(state: LoginState) {
        postLoginState(state)
        updateUserDetails(state)
    }

    private fun postLoginState(state: LoginState) {
        _loginState.value = state
    }

    private fun updateUserDetails(state: LoginState) {
        when (state) {
            LoginState.LOGGED_IN -> fetchUserDetails()
            LoginState.LOGGED_OUT -> postUserDetails(null)
        }
    }

    private fun fetchUserDetails() {
        viewModelScope.launch {
            val userDetailsResult = fetchUserDetailsUseCase.run(UseCase.None)

            runOnViewModelScope {
                userDetailsResult
                    .onSuccess(::postUserDetails)
                    .onFailure(::handleFailure)
            }
        }
    }

    private fun postUserDetails(userDetail: UserDetailUiModel?) {
        _userDetails.value = userDetail
    }

    fun onLoginClick() {
        navigate(ProfileFragmentDirections.toLogin())
    }
}
