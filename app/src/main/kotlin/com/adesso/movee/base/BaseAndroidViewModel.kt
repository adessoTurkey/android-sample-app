package com.adesso.movee.base

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.adesso.movee.R
import com.adesso.movee.internal.popup.PopUpType
import com.adesso.movee.internal.popup.PopupCallback
import com.adesso.movee.internal.popup.PopupUiModel
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.navigation.NavigationCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")
abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    private val _failurePopup = Channel<PopupUiModel>(Channel.CONFLATED)
    val failurePopup = _failurePopup.receiveAsFlow()

    private val _success = Channel<String>(Channel.CONFLATED)
    val success = _success.receiveAsFlow()

    private val _navigation = Channel<NavigationCommand>(Channel.CONFLATED)
    val navigation = _navigation.receiveAsFlow()

    protected open fun handleFailure(failure: Failure) {
        val (title, message) = when (failure) {
            is Failure.NoConnectivityError -> Pair(
                "",
                getString(R.string.common_error_network_connection)
            )

            is Failure.UnknownHostError -> Pair("", getString(R.string.common_error_unknown_host))
            is Failure.ServerError -> Pair("", failure.message)
            is Failure.JsonError, is Failure.EmptyResponse -> Pair(
                "",
                getString(R.string.common_error_invalid_response)
            )

            is Failure.FormValidationError -> Pair(
                getString(R.string.common_title_popup_form_validation),
                failure.message
                    ?: getString(R.string.common_error_invalid_form)
            )

            is Failure.IoError -> Pair("", getString(R.string.common_error_can_not_save_data))
            is Failure.UnknownError -> Pair(
                "",
                failure.exception.localizedMessage ?: getString(R.string.common_error_unknown)
            )

            is Failure.HttpError -> Pair(
                "",
                getString(R.string.common_error_http, failure.code.toString())
            )

            is Failure.TimeOutError -> Pair("", getString(R.string.common_error_timeout))
            else -> Pair("", failure.message ?: failure.toString())
        }

        _failurePopup.trySend(
            PopupUiModel(
                title = title,
                message = message,
                popUpType = PopUpType.ERROR
            )
        )
    }

    protected fun showSnackBar(message: String) {
        _success.trySend(message)
    }

    fun navigate(directions: NavDirections) {
        _navigation.trySend(NavigationCommand.ToDirection(directions))
    }

    fun navigate(deepLink: String) {
        _navigation.trySend(NavigationCommand.ToDeepLink(deepLink))
    }

    fun navigate(@StringRes deepLinkRes: Int) {
        navigate(getString(deepLinkRes))
    }

    fun navigate(model: PopupUiModel, callback: PopupCallback?) {
        _navigation.trySend(NavigationCommand.Popup(model, callback))
    }

    fun navigateBack() {
        _navigation.trySend(NavigationCommand.Back)
    }

    protected suspend fun <T> runOnViewModelScope(block: suspend CoroutineScope.() -> T): T {
        return withContext(viewModelScope.coroutineContext) {
            block.invoke(this)
        }
    }

    protected fun getString(@StringRes resId: Int): String {
        return getApplication<Application>().getString(resId)
    }

    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return getApplication<Application>().getString(resId, formatArgs)
    }
}
