package com.lefarmico.moviesfinder.ui.navigation.impl

import android.app.Activity
import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lefarmico.moviesfinder.ui.navigation.api.Dialog
import com.lefarmico.moviesfinder.ui.navigation.api.Notification
import com.lefarmico.moviesfinder.ui.navigation.api.Router
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination
import com.lefarmico.moviesfinder.ui.navigation.api.resolver.ScreenResolver
import javax.inject.Inject

class RouterImpl @Inject constructor(
    private val screenResolver: ScreenResolver
) : Router {

    private var navController: NavController? = null
    private var activity: Activity? = null
    private var fragmentManager: FragmentManager? = null

    override fun bind(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun bindNavController(navController: NavController) {
        TODO("Not yet implemented")
    }

    override fun bindNavigationUI(bottomNavigationView: BottomNavigationView) {
        TODO("Not yet implemented")
    }

    override fun navigate(
        screenDestination: ScreenDestination,
        data: Parcelable?,
        sharedElements: Map<Any, String>?
    ) {
        TODO("Not yet implemented")
    }

    override fun show(notification: Notification, data: Parcelable?, anchor: Any?) {
        TODO("Not yet implemented")
    }

    override fun showDialog(dialog: Dialog) {
        TODO("Not yet implemented")
    }

    override fun back() {
        TODO("Not yet implemented")
    }
}
