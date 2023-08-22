package com.adesso.movee.domain

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.transform

@Singleton
class ShouldRefreshPagingUseCase @Inject constructor(
    private val networkConnectionDispatcher: NetworkConnectionDispatcher,
) {
    private val _previousConnectionState = MutableStateFlow<NetworkConnection?>(null)

    fun execute(): Flow<Boolean> {
        return networkConnectionDispatcher.state.transform {
            emit(
                it == NetworkConnection.AVAILABLE &&
                    _previousConnectionState.value == NetworkConnection.UNAVAILABLE
            )

            _previousConnectionState.value = it
        }
    }
}
