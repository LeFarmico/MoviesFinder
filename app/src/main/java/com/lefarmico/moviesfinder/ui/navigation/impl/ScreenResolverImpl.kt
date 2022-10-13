package com.lefarmico.moviesfinder.ui.navigation.impl

import android.os.Parcelable
import android.util.Log
import androidx.navigation.NavController
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.ui.movie.MovieFragment
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination
import com.lefarmico.moviesfinder.ui.navigation.api.resolver.ScreenResolver

class ScreenResolverImpl : ScreenResolver {

    override fun navigate(
        navController: NavController?,
        data: Parcelable?,
        screenDestination: ScreenDestination
    ) {
        try {
            when (screenDestination) {
                ScreenDestination.FromHomeToMovieDestination -> {
                    navController!!.navigate(
                        R.id.action_movie_fragment_self,
                        data?.let { MovieFragment.createBundle(it) }
                    )
                }
                ScreenDestination.HomeDestination -> {
                    Log.i("ScreenResolver", "Not yet implemented")
                }
                ScreenDestination.MovieToSelfDestination -> {
                    navController!!.navigate(
                        R.id.action_movie_fragment_self,
                        data?.let { MovieFragment.createBundle(it) }
                    )
                }
                ScreenDestination.ProfileDestination -> {
                    Log.i("ScreenResolver", "Not yet implemented")
                }
            }
        } catch (e: NullPointerException) {
            Log.e("ScreenResolver", "Navigation controller is not bind", e)
        }
    }
}