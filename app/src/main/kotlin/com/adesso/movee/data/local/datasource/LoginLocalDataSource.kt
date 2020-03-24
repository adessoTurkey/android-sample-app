package com.adesso.movee.data.local.datasource

import android.content.SharedPreferences
import com.adesso.movee.data.local.delegate.StringPreference
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private var sessionPrefs by StringPreference(sharedPreferences, PREF_SESSION)

    fun insertSessionToken(token: String?) {
        sessionPrefs = token
    }

    fun getSessionToken(): String? = sessionPrefs

    companion object {
        private const val PREF_SESSION = "login_session_token"
    }
}