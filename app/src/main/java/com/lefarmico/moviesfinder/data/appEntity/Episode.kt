package com.lefarmico.moviesfinder.data.appEntity

data class Episode(
    val id: Int,
    val airDate: String,
    val episodeNumber: Int,
    val title: String,
    val seasonNumber: Int,
    val showId: Int,
    val rating: Int
)
