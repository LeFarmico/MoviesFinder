package com.lefarmico.moviesfinder.ui.navigation.api.fragmentRouter

sealed class FragmentDestination {

    object Movies : FragmentDestination()

    object Profile : FragmentDestination()
}
