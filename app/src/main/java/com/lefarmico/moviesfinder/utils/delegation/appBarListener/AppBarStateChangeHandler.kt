package com.lefarmico.moviesfinder.utils.delegation.appBarListener

import androidx.lifecycle.Lifecycle
import com.google.android.material.appbar.AppBarLayout
import com.lefarmico.moviesfinder.utils.component.appBar.AppBarStateChangeListener

interface AppBarStateChangeHandler {

    fun registerAppBarStateChangeHandler(
        appBarLayout: AppBarLayout?,
        lifecycle: Lifecycle,
        action: (AppBarStateChangeListener.State) -> Unit
    )
}
