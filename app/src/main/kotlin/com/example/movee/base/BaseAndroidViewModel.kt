package com.example.movee.base

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.movee.R
import com.example.movee.internal.popup.PopUpType
import com.example.movee.internal.popup.PopupCallback
import com.example.movee.internal.popup.PopupUiModel
import com.example.movee.internal.util.Event
import com.example.movee.internal.util.Failure
import com.example.movee.navigation.NavigationCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext

@Suppress("ConvertSecondaryConstructorToPrimary")
@SuppressLint("StaticFieldLeak")
abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    private val _failurePopup = MutableLiveData<Event<PopupUiModel>>()
    val failurePopup: LiveData<Event<PopupUiModel>> = _failurePopup

    private val _success = MutableLiveData<Event<String>>()
    val success: LiveData<Event<String>> = _success

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    private val viewModelJob = SupervisorJob()

    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    protected val bgScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    protected open fun handleFailure(failure: Failure) {
        val (title, message) = when (failure) {
            is Failure.NoConnectivityError -> Pair(
                "",
                getString(R.string.error_message_network_connection)
            )
            is Failure.UnknownHostError -> Pair("", getString(R.string.error_message_unknown_host))
            is Failure.ServerError -> Pair("", failure.message)
            is Failure.JsonError, is Failure.EmptyResponse -> Pair(
                "",
                getString(R.string.error_message_invalid_response)
            )
            is Failure.FormValidationError -> Pair(
                getString(R.string.popup_error_title_form_validation), failure.message
                    ?: getString(R.string.error_message_invalid_form)
            )
            is Failure.IoError -> Pair("", getString(R.string.error_message_can_not_save_data))
            is Failure.UnknownError -> Pair(
                "",
                failure.message ?: getString(R.string.error_unknown)
            )
            is Failure.HttpError -> Pair(
                "",
                getString(R.string.error_message_http, failure.code.toString())
            )
            is Failure.TimeOutError -> Pair("", getString(R.string.error_message_timeout))
            else -> Pair("", failure.message ?: failure.toString())
        }

        _failurePopup.value = Event(
            PopupUiModel(
                title = title,
                message = message,
                popUpType = PopUpType.ERROR
            )
        )
    }

    protected fun showSnackBar(message: String) {
        _success.value = Event(message)
    }

    fun navigate(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.ToDirection(directions))
    }

    fun navigate(deepLink: String) {
        _navigation.value = Event(NavigationCommand.ToDeepLink(deepLink))
    }

    fun navigate(@StringRes deepLinkRes: Int) {
        navigate(getString(deepLinkRes))
    }

    fun navigate(model: PopupUiModel, callback: PopupCallback?) {
        _navigation.value = Event(NavigationCommand.Popup(model, callback))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

    protected suspend fun onUIThread(block: suspend CoroutineScope.() -> Unit) {
        withContext(uiScope.coroutineContext) {
            block.invoke(this)
        }
    }

    protected fun getString(@StringRes resId: Int): String {
        return getApplication<Application>().getString(resId)
    }

    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return getApplication<Application>().getString(resId, formatArgs)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}