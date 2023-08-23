package com.adesso.movee.internal.extension

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun Fragment.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    context.toast(msg, duration)
}

inline fun <T> Fragment.collectFlow(
    flow: Flow<T>,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    lifecycleScope.launch {
        flow.flowWithLifecycle(viewLifecycleOwner.lifecycle, minActiveState).collectLatest(action)
    }
}
