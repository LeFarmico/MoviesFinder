package com.lefarmico.moviesfinder.ui.delegation.lifecycleScope

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class LifecycleScopeDelegationImpl() : LifecycleScopeDelegation, LifecycleEventObserver {

    private var lifecycle: Lifecycle? = null

    override val fragmentJob: Job = Job()
    override val fragmentScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + fragmentJob)

    override fun registerScope(lifecycle: Lifecycle) {
        this.lifecycle = lifecycle

        if (lifecycle.currentState >= Lifecycle.State.INITIALIZED) {
            lifecycle.addObserver(this)
        } else {
            fragmentJob.cancel()
        }

        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            fragmentJob.cancel()
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        try {
            if (lifecycle!!.currentState <= Lifecycle.State.DESTROYED) {
                lifecycle!!.removeObserver(this)
                fragmentJob.cancel()
            }
        } catch (e: NullPointerException) {
            throw (e)
        }
    }
}
