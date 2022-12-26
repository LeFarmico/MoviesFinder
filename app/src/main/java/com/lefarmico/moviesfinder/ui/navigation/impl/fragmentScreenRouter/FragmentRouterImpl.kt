package com.lefarmico.moviesfinder.ui.navigation.impl.fragmentScreenRouter

import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.ui.movies.MoviesFragment
import com.lefarmico.moviesfinder.ui.navigation.api.fragmentRouter.FragmentDestination
import com.lefarmico.moviesfinder.ui.navigation.api.fragmentRouter.FragmentRouter
import javax.inject.Inject

class FragmentRouterImpl @Inject constructor() : FragmentRouter {

    private var fragmentManager: FragmentManager? = null

    override fun bindFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    override fun navigate(
        screenDestination: FragmentDestination,
        data: Parcelable?,
        sharedElements: Map<Any, String>?
    ) {
        when (screenDestination) {
            FragmentDestination.Movies -> {
                fragmentManager!!.beginTransaction()
                    .replace(
                        R.id.home_fragment_container,
                        MoviesFragment::class.java,
                        null
                    ).commit()
            }
            FragmentDestination.Profile -> TODO()
        }
    }

    override fun back() {
        TODO("Not yet implemented")
    }
}
