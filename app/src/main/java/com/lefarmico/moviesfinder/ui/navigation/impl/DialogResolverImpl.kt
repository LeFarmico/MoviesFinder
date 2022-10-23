package com.lefarmico.moviesfinder.ui.navigation.impl

import android.util.Log
import androidx.fragment.app.FragmentManager
import com.lefarmico.moviesfinder.ui.navigation.api.Dialog
import com.lefarmico.moviesfinder.ui.navigation.api.resolver.DialogResolver
import javax.inject.Inject

class DialogResolverImpl @Inject constructor() : DialogResolver {

    override fun show(fragmentManager: FragmentManager, dialog: Dialog) {
        Log.i(this.toString(), "Dialog resolver not yet implemented!")
    }
}
