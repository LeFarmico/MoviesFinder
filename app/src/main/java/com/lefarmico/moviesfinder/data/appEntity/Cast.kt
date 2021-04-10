package com.lefarmico.moviesfinder.data.appEntity

data class Cast(
    val id: Int,
    val name: String,
    val profilePath: String? = "",
    val character: String = "",
    val posterPath: String = ""
)
