package com.lefarmico.moviesfinder.ui.common.delegation.onBackPress

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class OnBackPressHandlerImpl : OnBackPressHandler, DefaultLifecycleObserver {

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPress.invoke()
        }
    }

    private var onBackPress: () -> Unit = {}
    private var fragmentActivity: FragmentActivity? = null

    override fun registerOnBackPress(
        fragmentActivity: FragmentActivity?,
        lifecycle: Lifecycle,
        action: () -> Unit
    ) {
        this.fragmentActivity = fragmentActivity
        this.onBackPress = action
        lifecycle.addObserver(this)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        fragmentActivity?.onBackPressedDispatcher?.addCallback(onBackPressedCallback)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        onBackPressedCallback.remove()
    }
}
