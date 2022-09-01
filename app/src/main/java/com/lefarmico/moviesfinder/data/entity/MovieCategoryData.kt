package com.lefarmico.moviesfinder.data.entity

import com.lefarmico.moviesfinder.providers.CategoryProvider

data class MovieCategoryData(
    val categoryResourceId: Int,
    val categoryName: CategoryProvider.Category
)
