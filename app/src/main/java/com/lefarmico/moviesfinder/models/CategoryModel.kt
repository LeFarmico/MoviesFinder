package com.lefarmico.moviesfinder.models

import com.lefarmico.moviesfinder.providers.CategoryProvider

class CategoryModel(
    override val titleResource: Int,
    override val categoryType: CategoryProvider.Category,
    override var items: ItemsDataModel
) : ICategoryModel
