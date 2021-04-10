package com.lefarmico.moviesfinder.data.appEntity

data class Movie(
    override val id: Int,
    override val posterPath: String,
    override val title: String,
    override var isFavorite: Boolean,
    override val rating: Double,
    override val description: String,
    override val yourRate: Int,
    override val genres: List<String>,
    override val actors: List<Cast>,
    override val directors: List<Cast>,
    override val watchProviders: List<Provider> = listOf(),
    override val length: Int,
    override val photosPath: List<String>,
    override val releaseDate: String
) : MovieItem
