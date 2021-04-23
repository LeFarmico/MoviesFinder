package com.lefarmico.moviesfinder.data.appEntity

import com.lefarmico.moviesfinder.providers.CategoryProvider
import kotlinx.coroutines.flow.Flow

class Category(
    val titleResource: Int,
    val categoryType: CategoryProvider.Category,
    var itemsList: Flow<List<ItemHeaderImpl>>
)
