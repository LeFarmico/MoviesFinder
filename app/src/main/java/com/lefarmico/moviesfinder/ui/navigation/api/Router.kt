package com.lefarmico.moviesfinder.ui.navigation.api

import android.app.Activity
import android.os.Parcelable

interface Router {

    fun bind(activity: Activity)

    fun navigate(
        screenDestination: ScreenDestination,
        data: Parcelable? = null,
        sharedElements: Map<Any, String>? = null
    )

    fun show(notificationType: NotificationType)

    fun showDialog(
        dialog: Dialog
    )

    fun back()
}
