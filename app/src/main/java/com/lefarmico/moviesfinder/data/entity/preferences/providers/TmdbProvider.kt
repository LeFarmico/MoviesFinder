package com.lefarmico.moviesfinder.data.entity.preferences.providers

data class TmdbProvider(
    val tmdbBuy: List<TmdbBuy>?,
    val tmdbFlatrate: List<TmdbFlatrate>?,
    val link: String?,
    val rent: List<TmdbRent>?
)
