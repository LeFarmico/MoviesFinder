package com.lefarmico.moviesfinder.data.entity

data class SearchedItemData(
    val viewType: SearchType,
    val movieBriefData: MovieBriefData
)

sealed class SearchType(val typeNumber: Int) {
    object RecentlySearch : SearchType(1)
    object Search : SearchType(2)
}
