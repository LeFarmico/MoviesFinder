package com.lefarmico.moviesfinder.models

interface ICategoryModel {
    val id: Int
    var items: ItemsDataModel
    val titleResource: Int
}
