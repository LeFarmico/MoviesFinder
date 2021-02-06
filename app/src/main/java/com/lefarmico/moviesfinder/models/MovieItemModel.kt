
package com.lefarmico.moviesfinder.models

data class MovieItemModel(
    override val posterId: Int = -1,
    override val title: String = "",
) : Item {
    override var categoryIds: MutableList<Int> = mutableListOf()
    override var isFavorite: Boolean = false
}
