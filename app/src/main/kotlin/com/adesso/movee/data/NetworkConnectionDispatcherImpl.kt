package com.adesso.movee.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.adesso.movee.domain.NetworkConnection
import com.adesso.movee.domain.NetworkConnectionDispatcher
import com.adesso.movee.internal.extension.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Singleton
class NetworkConnectionDispatcherImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetworkConnectionDispatcher, CoroutineScope, DefaultLifecycleObserver {
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO
    private val _state = Channel<NetworkConnection>(Channel.CONFLATED)
    override val state = _state.receiveAsFlow()

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

            connectivityManager.registerDefaultNetworkCallback(object :
                    ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        _state.trySend(NetworkConnection.AVAILABLE)
                    }

                    override fun onLost(network: Network) {
                        _state.trySend(NetworkConnection.UNAVAILABLE)
                    }
                })
        } else {
            launch {
                while (true) {
                    delay(3000)

                    val state = if (context.isNetworkAvailable()) {
                        NetworkConnection.AVAILABLE
                    } else {
                        NetworkConnection.UNAVAILABLE
                    }

                    _state.send(state)
                }
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        cancel()
    }
}
