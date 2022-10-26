package com.lefarmico.moviesfinder.utils.extension

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun <T : Parcelable> Fragment.getArgumentsData(key: String): T? =
    requireArguments().getParcelable(key)

fun Fragment.removeArgument(key: String) =
    requireArguments().remove(key)

fun Fragment.getDrawable(@DrawableRes id: Int) =
    ContextCompat.getDrawable(requireContext(), id)
