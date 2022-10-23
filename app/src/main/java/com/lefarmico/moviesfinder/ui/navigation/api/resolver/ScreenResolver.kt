package com.lefarmico.moviesfinder.ui.navigation.api.resolver

import android.os.Parcelable
import androidx.navigation.NavController
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination

interface ScreenResolver {

    fun navigate(
        navController: NavController? = null,
        data: Parcelable? = null,
        screenDestination: ScreenDestination
    )
}
