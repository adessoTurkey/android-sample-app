package com.adesso.movee.internal.util

import androidx.lifecycle.Observer

class EventObserver<T>(private val eventUnHandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(t: Event<T>?) {
        t?.getContentIfNotHandled()?.let {
            eventUnHandledContent(it)
        }
    }
}
