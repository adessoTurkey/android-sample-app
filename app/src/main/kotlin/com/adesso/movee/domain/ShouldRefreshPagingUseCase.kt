package com.adesso.movee.domain

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.transform

class ShouldRefreshPagingUseCase @Inject constructor(
    private val networkConnectionDispatcher: NetworkConnectionDispatcher,
) {
    private val _previousNetworkState = MutableStateFlow<NetworkConnection?>(null)

    fun execute(): Flow<Boolean> {
        return networkConnectionDispatcher.state.transform {
            emit(
                it == NetworkConnection.AVAILABLE &&
                    _previousNetworkState.value == NetworkConnection.UNAVAILABLE
            )

            _previousNetworkState.value = it
        }
    }
}
