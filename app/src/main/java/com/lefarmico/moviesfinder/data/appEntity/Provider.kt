package com.lefarmico.moviesfinder.data.appEntity

data class Provider(
    val providerType: ProviderType? = null,
    val name: String,
    val providerId: Int,
    val logoPath: String,
    val displayPriority: Int,
    val movieDetailsId: Int = 0
) {
    enum class ProviderType {
        FLATRATE, BUY, RENT
    }
}
