package com.lefarmico.moviesfinder.adapter

import androidx.annotation.StringRes
import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.data.appEntity.Header
import com.lefarmico.moviesfinder.providers.CategoryProvider
import com.lefarmico.moviesfinder.utils.PaginationController

class MoviesConcatAdapterBuilder(
    private val paginationController: PaginationController
) {

    private val concatAdapter = ConcatAdapter()

    fun addHeader(@StringRes headerTitleResource: Int) {
        val titleGroupAdapter = TitleGroupAdapter().apply {
            setHeaderTitle(headerTitleResource)
        }
        concatAdapter.addAdapter(titleGroupAdapter)
    }

    fun addMoviesByCategory(category: CategoryProvider.Category, headerList: List<Header>) {
        val itemsAdapter = ItemsPlaceholderAdapter(paginationController)
        itemsAdapter.apply {
            this.categoryType = category
            setItems(headerList.toMutableList())
        }
        concatAdapter.addAdapter(itemsAdapter)
    }
    fun build() = concatAdapter
}
