package com.lefarmico.moviesfinder.ui.common.delegation

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle

interface OnBackPressListener {
    fun registerOnBackPress(
        fragmentActivity: FragmentActivity?,
        lifecycle: Lifecycle,
        action: () -> Unit
    )
}
