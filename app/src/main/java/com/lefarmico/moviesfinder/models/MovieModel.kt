package com.lefarmico.moviesfinder.models

data class MovieModel(
    override val id: Int,
    override val posterPath: String,
    override val title: String,
    override var isFavorite: Boolean,
    override val rating: Double,
    override val description: String,
    override val yourRate: Int,
    override val genres: List<String>,
    override val actors: List<CastModel>,
    override val directors: List<CastModel>,
    override val watchProviders: List<ProviderModel> = listOf(),
    override val length: Int,
    override val photosPath: List<String>,
    override val releaseDate: String
) : MovieItem
