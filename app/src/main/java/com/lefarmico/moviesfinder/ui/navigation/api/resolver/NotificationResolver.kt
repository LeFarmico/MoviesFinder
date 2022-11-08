package com.lefarmico.moviesfinder.ui.navigation.api.resolver

import android.app.Activity
import com.lefarmico.moviesfinder.ui.navigation.api.NotificationType

interface NotificationResolver {

    fun show(
        activity: Activity?,
        notificationType: NotificationType
    )
}
