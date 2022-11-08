package com.lefarmico.moviesfinder.ui.navigation.impl

import android.app.Activity
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.lefarmico.moviesfinder.ui.navigation.api.Dialog
import com.lefarmico.moviesfinder.ui.navigation.api.NotificationType
import com.lefarmico.moviesfinder.ui.navigation.api.Router
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination
import com.lefarmico.moviesfinder.ui.navigation.api.resolver.DialogResolver
import com.lefarmico.moviesfinder.ui.navigation.api.resolver.NotificationResolver
import com.lefarmico.moviesfinder.ui.navigation.api.resolver.ScreenResolver
import javax.inject.Inject

class RouterImpl @Inject constructor(
    private val screenResolver: ScreenResolver,
    private val notificationResolver: NotificationResolver,
    private val dialogResolver: DialogResolver
) : Router {

    private var activity: Activity? = null
    private var fragmentManager: FragmentManager? = null

    override fun bind(activity: Activity) {
        this.activity = activity
        this.fragmentManager = (activity as FragmentActivity).supportFragmentManager
    }

    override fun navigate(
        screenDestination: ScreenDestination,
        data: Parcelable?,
        sharedElements: Map<Any, String>?
    ) {
        screenResolver.navigate(fragmentManager, data, screenDestination)
    }

    override fun show(notificationType: NotificationType) {
        notificationResolver.show(activity, notificationType)
    }

    override fun showDialog(dialog: Dialog) {
        try {
            dialogResolver.show(fragmentManager!!, dialog)
        } catch (e: NullPointerException) {
            throw RuntimeException("Fragment Manager must not be null")
        }
    }

    override fun back() {
        try {
            fragmentManager!!.popBackStack()
        } catch (e: NullPointerException) {
            throw RuntimeException("Fragment Manager must not be null")
        }
    }
}
