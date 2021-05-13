package com.lefarmico.moviesfinder.data.appEntity

import com.lefarmico.moviesfinder.providers.CategoryProvider
import io.reactivex.rxjava3.core.Observable

class Category(
    val titleResource: Int,
    val categoryType: CategoryProvider.Category,
    var itemsList: Observable<List<ItemHeaderImpl>>
)
