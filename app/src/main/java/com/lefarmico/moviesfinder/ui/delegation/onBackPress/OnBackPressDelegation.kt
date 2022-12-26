package com.lefarmico.moviesfinder.ui.delegation.onBackPress

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle

interface OnBackPressDelegation {
    fun registerOnBackPress(
        fragmentActivity: FragmentActivity?,
        lifecycle: Lifecycle,
        action: () -> Unit
    )
}
