package com.lefarmico.moviesfinder.utils.delegation.appBarDragCallback

import androidx.lifecycle.Lifecycle
import com.google.android.material.appbar.AppBarLayout

interface AppBarDragCallback {

    fun registerDragCallback(
        appBarLayout: AppBarLayout?,
        lifecycle: Lifecycle,
        action: () -> Boolean
    )
}
