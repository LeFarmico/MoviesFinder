package com.lefarmico.moviesfinder.ui.navigation.impl

import android.app.Activity
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.lefarmico.moviesfinder.ui.navigation.api.NotificationType
import com.lefarmico.moviesfinder.ui.navigation.api.resolver.NotificationResolver
import javax.inject.Inject

class NotificationResolverImpl @Inject constructor() : NotificationResolver {

    override fun show(activity: Activity?, notificationType: NotificationType) {
        try {
            when (notificationType) {
                is NotificationType.SnackBar -> showSnackBar(activity!!, notificationType)
                is NotificationType.Toast -> showToast(activity!!, notificationType)
            }
        } catch (e: NullPointerException) {
            throw RuntimeException("Activity is not bind to NavController")
        }
    }

    // TODO add undo button
    private fun showSnackBar(
        activity: Activity,
        snackBarType: NotificationType.SnackBar
    ) {
        val activityView = activity.findViewById(android.R.id.content) as View
        val message = snackBarType.message

        Snackbar.make(activityView, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showToast(
        activity: Activity,
        toastType: NotificationType.Toast
    ) {
        Toast.makeText(activity, toastType.message, Toast.LENGTH_SHORT).show()
    }
}
