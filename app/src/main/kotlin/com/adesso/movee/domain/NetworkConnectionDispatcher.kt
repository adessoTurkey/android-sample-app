package com.adesso.movee.domain

import kotlinx.coroutines.flow.Flow

interface NetworkConnectionDispatcher {
    val state: Flow<NetworkConnection>
    fun getConnectionState(): NetworkConnection
}
