package com.lefarmico.moviesfinder.data.entity.preferences.providers

data class Provider(
    val buy: List<Buy>?,
    val flatrate: List<Flatrate>?,
    val link: String?,
    val rent: List<Rent>?
)
