package com.lefarmico.moviesfinder.ui.delegation.appBarDragCallback

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.appbar.AppBarLayout

class AppBarDragCallbackDelegationImpl : AppBarDragCallbackDelegation, DefaultLifecycleObserver {

    private var dragCallbackBehavior = object : AppBarLayout.Behavior.DragCallback() {
        override fun canDrag(appBarLayout: AppBarLayout): Boolean {
            return canDragCallback.invoke()
        }
    }

    private var appBarLayout: AppBarLayout? = null
    private var canDragCallback: () -> Boolean = { true }

    override fun registerDragCallback(
        appBarLayout: AppBarLayout?,
        lifecycle: Lifecycle,
        action: () -> Boolean
    ) {
        this.appBarLayout = appBarLayout
        canDragCallback = action
        lifecycle.addObserver(this)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        val layoutParams = appBarLayout?.layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            layoutParams.apply {
                if (behavior == null)
                    behavior = AppBarLayout.Behavior()

                (behavior as AppBarLayout.Behavior)
                    .setDragCallback(dragCallbackBehavior)
            }
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        val layoutParams = appBarLayout?.layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            layoutParams.apply {
                if (behavior == null)
                    behavior = AppBarLayout.Behavior()

                (behavior as AppBarLayout.Behavior)
                    .setDragCallback(null)
            }
        }
    }
}
