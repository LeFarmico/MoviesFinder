package com.lefarmico.moviesfinder.data.appEntity

data class Cast(
    val personId: Int,
    val name: String,
    val profilePath: String? = "",
    val character: String = "",
    val posterPath: String = "",
    val movieDetailsId: Int = 0
)
