package com.lefarmico.moviesfinder.ui.navigation.api.resolver

import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination

interface ScreenResolver {

    fun navigate(
        fragmentManager: FragmentManager? = null,
        data: Parcelable? = null,
        screenDestination: ScreenDestination
    )
}
