package com.lefarmico.moviesfinder.ui.base

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

abstract class BaseApp : Application(), LifecycleEventObserver {

    var isAppInForeground: Boolean = true

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> onAppForeground()
            Lifecycle.Event.ON_STOP -> onAppBackGround()
            else -> {}
        }
    }

    open fun onAppBackGround() {
        isAppInForeground = false
    }

    open fun onAppForeground() {
        isAppInForeground = true
    }
}
