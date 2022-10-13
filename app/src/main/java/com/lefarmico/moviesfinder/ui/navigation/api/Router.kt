package com.lefarmico.moviesfinder.ui.navigation.api

import android.app.Activity
import android.os.Parcelable
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView

interface Router {

    fun bind(activity: Activity)

    fun bindNavController(navController: NavController)

    fun bindNavigationUI(bottomNavigationView: BottomNavigationView)

    fun navigate(
        screenDestination: ScreenDestination,
        data: Parcelable? = null,
        sharedElements: Map<Any, String>? = null
    )

    fun show(
        notification: Notification,
        data: Parcelable? = null,
        anchor: Any? = null
    )

    fun showDialog(
        dialog: Dialog
    )

    fun back()
}
