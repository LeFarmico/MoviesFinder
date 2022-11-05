package com.lefarmico.moviesfinder.utils.delegation.onBackPress

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle

interface OnBackPressHandler {
    fun registerOnBackPress(
        fragmentActivity: FragmentActivity?,
        lifecycle: Lifecycle,
        action: () -> Unit
    )
}
