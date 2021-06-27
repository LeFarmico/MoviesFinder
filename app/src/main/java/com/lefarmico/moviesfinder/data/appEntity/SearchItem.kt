package com.lefarmico.moviesfinder.data.appEntity

data class SearchItem(
    val viewType: SearchType,
    val itemHeader: ItemHeader
)

enum class SearchType(val typeNumber: Int) {
    RECENTLY_SEARCHED(1), SEARCH(2)
}
