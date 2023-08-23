package com.adesso.movee.domain

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class ShouldRefreshPagingUseCase @Inject constructor(
    private val networkConnectionDispatcher: NetworkConnectionDispatcher,
) {
    private var previousConnectionState = networkConnectionDispatcher.getConnectionState()

    fun execute(): Flow<Boolean> {
        return networkConnectionDispatcher.state.transform {
            emit(
                it == NetworkConnection.AVAILABLE &&
                    previousConnectionState == NetworkConnection.UNAVAILABLE
            )

            previousConnectionState = it
        }
    }
}
