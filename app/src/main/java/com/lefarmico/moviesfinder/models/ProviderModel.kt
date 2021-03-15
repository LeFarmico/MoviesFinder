package com.lefarmico.moviesfinder.models

data class ProviderModel(
    val providerType: ProviderType,
    val name: String,
    val id: Int,
    val logoPath: String,
    val displayPriority: Int
)
enum class ProviderType {
    FLATRATE, BUY, RENT
}
