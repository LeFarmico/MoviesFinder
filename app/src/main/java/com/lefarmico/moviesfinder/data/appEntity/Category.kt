package com.lefarmico.moviesfinder.data.appEntity

import androidx.lifecycle.LiveData
import com.lefarmico.moviesfinder.providers.CategoryProvider

class Category(
    val titleResource: Int,
    val categoryType: CategoryProvider.Category,
    var itemsList: LiveData<List<ItemHeaderImpl>>
)
