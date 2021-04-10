package com.lefarmico.moviesfinder.data.entity.appEntity

data class Provider(
    val providerType: ProviderType,
    val name: String,
    val id: Int,
    val logoPath: String,
    val displayPriority: Int
) {
    enum class ProviderType {
        FLATRATE, BUY, RENT
    }
}
