package com.lefarmico.moviesfinder.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieCastData(
    val personId: Int,
    val name: String,
    val profilePath: String? = "",
    val character: String = "",
    val posterPath: String = "",
    val movieDetailsId: Int = 0
) : Parcelable
