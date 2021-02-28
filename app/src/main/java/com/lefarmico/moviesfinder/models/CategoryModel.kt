package com.lefarmico.moviesfinder.models

class CategoryModel(
    override val titleResource: Int,
    override val id: Int,
    override var items: ItemsDataModel
) : ICategoryModel
