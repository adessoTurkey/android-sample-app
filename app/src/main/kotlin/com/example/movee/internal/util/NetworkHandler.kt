package com.example.movee.internal.util

import android.content.Context
import com.example.movee.internal.extension.networkInfo
import javax.inject.Singleton

@Singleton
class NetworkHandler(private val context: Context) {
    val isConnected: Boolean
        get() = context.networkInfo?.isConnected ?: false
}