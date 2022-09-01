package com.lefarmico.moviesfinder.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieProviderData(
    val providerType: ProviderType? = null,
    val name: String,
    val providerId: Int,
    val logoPath: String,
    val displayPriority: Int,
    val movieDetailsId: Int = 0
) : Parcelable {
    enum class ProviderType {
        FLATRATE, BUY, RENT
    }
}
