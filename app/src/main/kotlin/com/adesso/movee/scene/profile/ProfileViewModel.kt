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
import javax.inject.Inject

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
        getLoginStateUseCase.invoke(viewModelScope, UseCase.None) {
            it.either(::handleFailure, ::handleLoginStateSuccess)
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
        fetchUserDetailsUseCase.invoke(viewModelScope, UseCase.None) {
            it.either(::handleFailure, ::postUserDetails)
        }
    }

    private fun postUserDetails(userDetail: UserDetailUiModel?) {
        _userDetails.value = userDetail
    }

    fun onLoginClick() {
        navigate(ProfileFragmentDirections.toLogin())
    }
}
