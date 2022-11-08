package com.lefarmico.moviesfinder.ui.navigation.impl

import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.ui.home.HomeFragment
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
                ScreenDestination.FromHomeToMovie -> {
                    fragmentManager!!.beginTransaction()
                        .addToBackStack(
                            "MovieFragment${fragmentManager.backStackEntryCount}"
                        )
                        .add(
                            R.id.main_fragment_container,
                            MovieFragment::class.java,
                            data?.let { MovieFragment.createBundle(it) }
                        ).commit()
                }
                ScreenDestination.Home -> {
                    fragmentManager!!.beginTransaction()
                        .addToBackStack(
                            "HomeFragment${fragmentManager.backStackEntryCount}"
                        )
                        .add(
                            R.id.main_fragment_container,
                            HomeFragment::class.java,
                            null
                        ).commit()
                }
                ScreenDestination.MovieToSelf -> {
                    fragmentManager!!.beginTransaction()
                        .addToBackStack(
                            "MovieFragment${fragmentManager.backStackEntryCount}"
                        )
                        .add(
                            R.id.main_fragment_container,
                            MovieFragment::class.java,
                            data?.let { MovieFragment.createBundle(it) }
                        ).commit()
                }
                ScreenDestination.Profile -> {
                    // TODO Implement Profile Screen
                    throw NotImplementedError("This Screen is not yet implemented")
                }
            }
        } catch (e: NullPointerException) {
            throw RuntimeException("Fragment Manager is not bind")
        }
    }
}
