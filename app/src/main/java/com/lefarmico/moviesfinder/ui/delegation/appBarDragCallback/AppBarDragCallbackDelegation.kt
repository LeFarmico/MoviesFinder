package com.lefarmico.moviesfinder.ui.delegation.appBarDragCallback

import androidx.lifecycle.Lifecycle
import com.google.android.material.appbar.AppBarLayout

interface AppBarDragCallbackDelegation {

    fun registerDragCallback(
        appBarLayout: AppBarLayout?,
        lifecycle: Lifecycle,
        action: () -> Boolean
    )
}
