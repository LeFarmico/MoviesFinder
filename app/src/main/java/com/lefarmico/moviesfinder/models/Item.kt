
package com.lefarmico.moviesfinder.models

import java.io.Serializable

interface Item : Serializable {
    val posterId: Int
    val title: String
    var isFavorite: Boolean
    var categoryIds: MutableList<Int>
}
