
package com.lefarmico.moviesfinder.models

import java.io.Serializable

interface Item : Serializable {
    val posterId: String
    val title: String
    var isFavorite: Boolean
    val genreIds: List<Int>
    val rating: Double
    val description: String
}
