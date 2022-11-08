package com.lefarmico.moviesfinder.ui.navigation.api

sealed class ScreenDestination() {

    object Home : ScreenDestination()

    object Profile : ScreenDestination()

    object FromHomeToMovie : ScreenDestination()

    object MovieToSelf : ScreenDestination()

    object ChildMovies : ScreenDestination()
}
