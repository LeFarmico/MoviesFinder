package com.lefarmico.moviesfinder.ui.navigation.api.resolver

import androidx.fragment.app.FragmentManager
import com.lefarmico.moviesfinder.ui.navigation.api.Dialog

interface DialogResolver {

    fun show(
        fragmentManager: FragmentManager,
        dialog: Dialog
    )
}
