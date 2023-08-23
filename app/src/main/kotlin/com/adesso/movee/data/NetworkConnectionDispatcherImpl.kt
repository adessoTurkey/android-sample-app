package com.adesso.movee.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.adesso.movee.domain.NetworkConnection
import com.adesso.movee.domain.NetworkConnectionDispatcher
import com.adesso.movee.internal.extension.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Singleton
class NetworkConnectionDispatcherImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetworkConnectionDispatcher, DefaultLifecycleObserver {
    private val _state = Channel<NetworkConnection>(Channel.CONFLATED)
    override val state = _state.receiveAsFlow()
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _state.trySend(NetworkConnection.AVAILABLE)
        }

        override fun onLost(network: Network) {
            _state.trySend(NetworkConnection.UNAVAILABLE)
        }
    }

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        _state.trySend(getConnectionState())
    }

    override fun getConnectionState(): NetworkConnection {
        return if (context.isNetworkAvailable()) {
            NetworkConnection.AVAILABLE
        } else {
            NetworkConnection.UNAVAILABLE
        }
    }

    private fun registerNetworkCallback() {
        connectivityManager?.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            networkCallback
        )
    }

    override fun onStart(owner: LifecycleOwner) {
        registerNetworkCallback()
    }

    override fun onStop(owner: LifecycleOwner) {
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }
}
