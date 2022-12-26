package com.lefarmico.moviesfinder.ui.delegation.appBarListener

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.appbar.AppBarLayout
import com.lefarmico.moviesfinder.utils.component.appBar.AppBarStateChangeListener

class AppBarStateHandlerDelegationImpl : AppBarStateHandlerDelegation, DefaultLifecycleObserver {

    private var stateChangeListener = object : AppBarStateChangeListener() {
        override fun onStateChanged(appBarLayout: AppBarLayout?, state: State) {
            onStateChange(state)
        }
    }

    private var appBarLayout: AppBarLayout? = null
    private var onStateChange: (AppBarStateChangeListener.State) -> Unit = {}

    override fun registerAppBarStateChangeHandler(
        appBarLayout: AppBarLayout?,
        lifecycle: Lifecycle,
        action: (AppBarStateChangeListener.State) -> Unit
    ) {
        lifecycle.addObserver(this)
        this.appBarLayout = appBarLayout
        onStateChange = action
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        appBarLayout?.addOnOffsetChangedListener(stateChangeListener)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        appBarLayout?.removeOnOffsetChangedListener(stateChangeListener)
    }
}
