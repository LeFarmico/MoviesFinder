
package com.lefarmico.moviesfinder.models

import java.io.Serializable

interface Item : Serializable {
    val id: Int
    val posterPath: String
    val title: String
    var isFavorite: Boolean
    val genreIds: List<Int>
    val rating: Double
    val description: String
    val genres: List<String>
}
