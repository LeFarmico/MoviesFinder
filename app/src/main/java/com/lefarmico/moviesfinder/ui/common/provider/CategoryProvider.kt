package com.lefarmico.moviesfinder.ui.common.provider

import com.lefarmico.moviesfinder.R

class CategoryProvider {

    companion object {
        val POPULAR_CATEGORY = Category.POPULAR_CATEGORY
        val UPCOMING_CATEGORY = Category.UPCOMING_CATEGORY
        val TOP_RATED_CATEGORY = Category.TOP_RATED_CATEGORY
        val NOW_PLAYING_CATEGORY = Category.NOW_PLAYING_CATEGORY
    }

    enum class Category(val categoryTitle: String) {
        POPULAR_CATEGORY("popular") {
            override fun getResource(): Int {
                return R.string.popular
            }
        },
        UPCOMING_CATEGORY("upcoming") {
            override fun getResource(): Int {
                return R.string.upcoming
            }
        },
        TOP_RATED_CATEGORY("top_rated") {
            override fun getResource(): Int {
                return R.string.top_rated
            }
        },
        NOW_PLAYING_CATEGORY("now_playing") {
            override fun getResource(): Int {
                return R.string.now_playing
            }
        };

        abstract fun getResource(): Int
    }
}
