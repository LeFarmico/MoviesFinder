package com.lefarmico.moviesfinder.models

data class SeasonModel(
    val id: Int,
    val airDate: String,
    val episodesCount: Int,
    val title: String,
    val posterPath: String,
    val seasonNumber: Int
)
