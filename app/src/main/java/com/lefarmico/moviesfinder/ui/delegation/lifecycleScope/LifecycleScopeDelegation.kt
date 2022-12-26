package com.lefarmico.moviesfinder.ui.delegation.lifecycleScope

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface LifecycleScopeDelegation {
    val fragmentJob: Job
    val fragmentScope: CoroutineScope

    fun registerScope(lifecycle: Lifecycle)
}
