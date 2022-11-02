package com.lefarmico.moviesfinder.ui.common.delegation.onBackPress

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle

interface OnBackPressHandler {
    fun registerOnBackPress(
        fragmentActivity: FragmentActivity?,
        lifecycle: Lifecycle,
        action: () -> Unit
    )
}
