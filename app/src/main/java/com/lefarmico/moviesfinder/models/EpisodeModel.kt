package com.lefarmico.moviesfinder.models

data class EpisodeModel(
    val id: Int,
    val airDate: String,
    val episodeNumber: Int,
    val title: String,
    val seasonNumber: Int,
    val showId: Int,
    val rating: Int
)
