
package com.lefarmico.moviesfinder.data

import java.io.Serializable

interface Item : Serializable {
    val posterId: Int
    val title: String
    var isFavorite: Boolean
}
