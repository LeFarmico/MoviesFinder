package com.lefarmico.moviesfinder.data.entity

import com.lefarmico.moviesfinder.R

sealed class CategoryData(
    val categoryRequestTitle: String,
    val categoryResource: Int
) {
    object PopularCategory : CategoryData("popular", R.string.popular)
    object UpcomingCategory : CategoryData("upcoming", R.string.upcoming)
    object TopRatedCategory : CategoryData("top_rated", R.string.top_rated)
    object NowPlayingCategory : CategoryData("now_playing", R.string.now_playing)
}
