package com.lefarmico.moviesfinder.ui.navigation.impl

import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.ui.movie.MovieFragment
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination
import com.lefarmico.moviesfinder.ui.navigation.api.resolver.ScreenResolver
import javax.inject.Inject

class ScreenResolverImpl @Inject constructor() : ScreenResolver {

    override fun navigate(
        fragmentManager: FragmentManager?,
        data: Parcelable?,
        screenDestination: ScreenDestination
    ) {
        try {
            when (screenDestination) {
                ScreenDestination.FromHomeToMovieDestination -> {
                    fragmentManager!!.beginTransaction()
                        .addToBackStack(
                            "MovieFragment${fragmentManager.backStackEntryCount}"
                        )
                        .add(
                            R.id.nav_host_fragment,
                            MovieFragment::class.java,
                            data?.let { MovieFragment.createBundle(it) }
                        ).commit()
                }
                ScreenDestination.HomeDestination -> {
                    Log.i("ScreenResolver", "Not yet implemented")
                }
                ScreenDestination.MovieToSelfDestination -> {
                    fragmentManager!!.beginTransaction()
                        .addToBackStack(
                            "MovieFragment${fragmentManager.backStackEntryCount}"
                        )
                        .add(
                            R.id.nav_host_fragment,
                            MovieFragment::class.java,
                            data?.let { MovieFragment.createBundle(it) }
                        ).commit()
                }
                ScreenDestination.ProfileDestination -> {
                    Log.i("ScreenResolver", "Not yet implemented")
                }
            }
        } catch (e: NullPointerException) {
            throw RuntimeException("Fragment Manager is not bind")
        }
    }
}
