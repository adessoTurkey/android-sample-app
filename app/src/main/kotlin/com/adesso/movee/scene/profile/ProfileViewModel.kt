package com.adesso.movee.scene.profile

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchUserDetailsUseCase
import com.adesso.movee.domain.GetLoginStateUseCase
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.LoginState
import com.adesso.movee.uimodel.UserDetailUiModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModel @Inject constructor(
    private val fetchUserDetailsUseCase: FetchUserDetailsUseCase,
    private val getLoginStateUseCase: GetLoginStateUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _userDetails = MutableStateFlow<UserDetailUiModel?>(null)
    val userDetails: StateFlow<UserDetailUiModel?> get() = _userDetails

    private val _loginState = MutableStateFlow<LoginState?>(null)

    val shouldShowUserDetails: StateFlow<Boolean> =
        _userDetails.mapLatest { it != null }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val shouldShowLoginView: StateFlow<Boolean> =
        _loginState.mapLatest { it == LoginState.LOGGED_OUT }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

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
