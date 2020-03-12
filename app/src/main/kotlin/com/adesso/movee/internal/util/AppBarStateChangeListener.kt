package com.adesso.movee.internal.util

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {
    enum class State {
        EXPANDED, COLLAPSED, IDLE
    }

    private var currentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        currentState = when {
            offset == 0 -> {
                if (currentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED)
                }
                State.EXPANDED
            }
            abs(offset) >= appBarLayout.totalScrollRange -> {
                if (currentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED)
                }
                State.COLLAPSED
            }
            else -> {
                if (currentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE)
                }
                State.IDLE
            }
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)
}

fun AppBarLayout.addAppBarStateChangeListener(
    onStateChange: (appBar: AppBarLayout, state: AppBarStateChangeListener.State) -> Unit
) {
    addOnOffsetChangedListener(object : AppBarStateChangeListener() {
        override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
            onStateChange.invoke(appBarLayout, state)
        }
    })
}