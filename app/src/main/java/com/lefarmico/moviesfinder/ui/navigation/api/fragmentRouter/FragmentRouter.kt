package com.lefarmico.moviesfinder.ui.navigation.api.fragmentRouter

import android.os.Parcelable
import androidx.fragment.app.FragmentManager

interface FragmentRouter {

    fun bindFragmentManager(fragmentManager: FragmentManager)

    fun navigate(
        screenDestination: FragmentDestination,
        data: Parcelable? = null,
        sharedElements: Map<Any, String>? = null
    )

    fun back()
}
