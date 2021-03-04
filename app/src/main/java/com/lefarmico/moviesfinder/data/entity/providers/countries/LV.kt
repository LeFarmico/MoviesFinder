package com.lefarmico.moviesfinder.data.entity.providers.countries

import com.lefarmico.moviesfinder.data.entity.providers.Buy
import com.lefarmico.moviesfinder.data.entity.providers.Flatrate

data class LV(
    val buy: List<Buy>,
    val flatrate: List<Flatrate>,
    val link: String,
)