package com.adesso.movee.internal.util

import android.content.Context
import com.adesso.movee.internal.extension.isNetworkAvailable
import javax.inject.Singleton

@Singleton
class NetworkHandler(private val context: Context) {
    val isConnected: Boolean
        get() = context.isNetworkAvailable()
}
