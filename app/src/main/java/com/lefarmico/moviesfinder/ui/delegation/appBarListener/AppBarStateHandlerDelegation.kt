package com.lefarmico.moviesfinder.ui.delegation.appBarListener

import androidx.lifecycle.Lifecycle
import com.google.android.material.appbar.AppBarLayout
import com.lefarmico.moviesfinder.utils.component.appBar.AppBarStateChangeListener

interface AppBarStateHandlerDelegation {

    fun registerAppBarStateChangeHandler(
        appBarLayout: AppBarLayout?,
        lifecycle: Lifecycle,
        action: (AppBarStateChangeListener.State) -> Unit
    )
}
