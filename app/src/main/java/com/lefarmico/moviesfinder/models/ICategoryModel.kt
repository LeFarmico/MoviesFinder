package com.lefarmico.moviesfinder.models

import com.lefarmico.moviesfinder.providers.CategoryProvider

interface ICategoryModel {
    val categoryType: CategoryProvider.Category
    var items: ItemsDataModel
    val titleResource: Int
}
