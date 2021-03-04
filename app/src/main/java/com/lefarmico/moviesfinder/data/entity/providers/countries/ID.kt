package com.lefarmico.moviesfinder.data.entity.providers.countries

import com.lefarmico.moviesfinder.data.entity.providers.Buy
import com.lefarmico.moviesfinder.data.entity.providers.Flatrate
import com.lefarmico.moviesfinder.data.entity.providers.Rent

data class ID(
    val buy: List<Buy>,
    val flatrate: List<Flatrate>,
    val link: String,
    val rent: List<Rent>
)