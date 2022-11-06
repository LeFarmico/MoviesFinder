package com.lefarmico.moviesfinder.utils.logger

import androidx.lifecycle.Lifecycle

interface LifecycleLogger {

    fun registerLifecycleLogger(
        lifecycle: Lifecycle,
        componentClass: Class<*>,
        tag: String,
        isDebugOnly: Boolean = false
    )
}
