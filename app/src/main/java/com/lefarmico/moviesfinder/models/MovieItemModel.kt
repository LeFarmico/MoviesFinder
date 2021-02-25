
package com.lefarmico.moviesfinder.models

data class MovieItemModel(
    override val posterId: String = "",
    override val title: String = "",
    override val genreIds: List<Int> = mutableListOf(),
    override val rating: Double = 0.0,
    override val description: String = "",
    override var isFavorite: Boolean = false
) : Item
