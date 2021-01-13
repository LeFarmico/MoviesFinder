
package com.lefarmico.moviesfinder.data

data class MovieItem(
    override val posterId: Int = -1,
    override val title: String = "",
) : Item {
    override var isFavorite: Boolean = false
}
