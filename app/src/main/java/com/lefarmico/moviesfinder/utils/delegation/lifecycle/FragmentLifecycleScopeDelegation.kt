package com.lefarmico.moviesfinder.utils.delegation.lifecycle

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface FragmentLifecycleScopeDelegation {
    val fragmentJob: Job
    val fragmentScope: CoroutineScope

    fun registerFragmentScope(lifecycle: Lifecycle)
}
