
package com.lefarmico.moviesfinder.models

data class ItemHeaderModel(
    override val id: Int,
    override val posterPath: String = "",
    override val title: String = "",
    override val rating: Double = 0.0,
    override val description: String = "",
    override var isFavorite: Boolean = false,
    override val yourRate: Int = 0,
    override val releaseDate: String
) : ItemHeader
