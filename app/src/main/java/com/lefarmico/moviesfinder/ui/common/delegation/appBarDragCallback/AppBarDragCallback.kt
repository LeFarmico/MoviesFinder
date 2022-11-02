package com.lefarmico.moviesfinder.ui.common.delegation.appBarDragCallback

import androidx.lifecycle.Lifecycle
import com.google.android.material.appbar.AppBarLayout

interface AppBarDragCallback {

    fun registerDragCallback(
        appBarLayout: AppBarLayout?,
        lifecycle: Lifecycle,
        action: () -> Boolean
    )
}
