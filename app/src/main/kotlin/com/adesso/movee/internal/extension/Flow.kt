package com.adesso.movee.internal.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collect(
    scope: CoroutineScope,
    crossinline action: suspend (T) -> Unit
) {
    scope.launch {
        collect { action(it) }
    }
}

inline fun <T> Flow<T?>.collectNonNull(
    scope: CoroutineScope,
    crossinline action: suspend (T) -> Unit
) {
    scope.launch {
        collect { value ->
            value?.let { action(it) }
        }
    }
}

inline fun <T> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
            .collect { action.invoke(it) }
    }
}

inline fun <T> Flow<T?>.collectWithLifecycleNonNull(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
            .collect { value ->
                value?.let { action.invoke(it) }
            }
    }
}
