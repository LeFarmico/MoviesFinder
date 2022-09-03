package com.lefarmico.moviesfinder.data.entity

import com.lefarmico.moviesfinder.ui.common.provider.CategoryProvider

data class MovieCategoryData(
    val categoryResourceId: Int,
    val categoryName: CategoryProvider.Category
)
