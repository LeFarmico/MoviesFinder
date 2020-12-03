package com.lefarmico.moviesfinder.adapterDelegates.item

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem(val poster: Int = -1, val title: String = "") : Item, Parcelable