package com.lefarmico.moviesfinder.data.entity.appEntity

import com.lefarmico.moviesfinder.providers.CategoryProvider

class Category(
    val titleResource: Int,
    val categoryType: CategoryProvider.Category,
    var itemsList: MutableList<ItemHeader>
)
