package com.lefarmico.moviesfinder.ui.navigation.api.params

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieFragmentParams(
    val movieId: Int
) : Parcelable
