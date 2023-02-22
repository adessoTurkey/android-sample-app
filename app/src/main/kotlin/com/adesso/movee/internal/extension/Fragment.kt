package com.adesso.movee.internal.extension

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.flow.Flow

fun Fragment.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    context.toast(msg, duration)
}

inline fun <T> Fragment.collectFlow(
    flow: Flow<T>,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (T) -> Unit
) {
    flow.collectWithLifecycle(viewLifecycleOwner, minActiveState, action)
}

inline fun <T> Fragment.collectFlowNonNull(
    flow: Flow<T?>,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (T) -> Unit
) {
    flow.collectWithLifecycleNonNull(viewLifecycleOwner, minActiveState, action)
}
