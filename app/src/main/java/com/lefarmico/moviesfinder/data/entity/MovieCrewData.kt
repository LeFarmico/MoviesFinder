package com.lefarmico.moviesfinder.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieCrewData(
    val id: Int,
    val creditId: String,
    val department: String,
    val name: String,
    val profilePath: String?
) : Parcelable
