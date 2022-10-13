package com.lefarmico.moviesfinder.ui.navigation.api

sealed class ScreenDestination() {

    object HomeDestination : ScreenDestination()

    object ProfileDestination : ScreenDestination()

    object FromHomeToMovieDestination : ScreenDestination()

    object MovieToSelfDestination : ScreenDestination()
}
