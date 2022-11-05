package com.lefarmico.moviesfinder.utils.logger

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.lefarmico.moviesfinder.BuildConfig

class LifecycleLoggerImpl : LifecycleLogger, DefaultLifecycleObserver {

    private var componentClass: Class<*>? = null
    private var logTag: String? = null

    override fun registerLifecycleLogger(
        lifecycle: Lifecycle,
        componentClass: Class<*>,
        tag: String,
        isDebugOnly: Boolean
    ) {
        if (!BuildConfig.DEBUG && isDebugOnly)
            return

        lifecycle.addObserver(this)
        this.componentClass = componentClass
        this.logTag = tag
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d(
            logTag,
            message("onCreate()")
        )
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d(
            logTag,
            message("onStart()")
        )
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d(
            logTag,
            message("onResume()")
        )
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d(
            logTag,
            message("onPause()")
        )
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d(
            logTag,
            message("onStop()")
        )
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d(
            logTag,
            message("onDestroy()")
        )
    }

    private fun message(lifecycleEvent: String) =
        "[Component] ${componentClass?.name}" +
            " \n [Lifecycle event] $lifecycleEvent"
}
