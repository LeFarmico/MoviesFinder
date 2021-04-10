package com.lefarmico.moviesfinder.data.appEntity

data class Season(
    val id: Int,
    val airDate: String,
    val episodesCount: Int,
    val title: String,
    val posterPath: String,
    val seasonNumber: Int
)
