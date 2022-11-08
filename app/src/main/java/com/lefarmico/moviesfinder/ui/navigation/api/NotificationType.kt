package com.lefarmico.moviesfinder.ui.navigation.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class NotificationType {

    @Parcelize
    data class Toast(val message: String) : NotificationType(), Parcelable

    @Parcelize
    data class SnackBar(
        val message: String,
        val listener: (() -> Unit)? = null
    ) : NotificationType(), Parcelable
}
