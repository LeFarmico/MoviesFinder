package com.lefarmico.moviesfinder.models

data class CastModel(
    val id: Int,
    val name: String,
    val profilePath: String? = "",
    val character: String = "",
    val posterPath: String = ""
)
