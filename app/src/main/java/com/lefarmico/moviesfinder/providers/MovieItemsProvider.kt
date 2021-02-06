
package com.lefarmico.moviesfinder.providers

import android.util.Log
import com.lefarmico.moviesfinder.data.DataPlaceholder
import com.lefarmico.moviesfinder.models.Item
import com.lefarmico.moviesfinder.models.ItemsDataModel
import com.lefarmico.moviesfinder.models.CategoryModel
import com.lefarmico.moviesfinder.presenters.MainMenuPresenter
import javax.inject.Inject

class MovieItemsProvider @Inject constructor(
    val presenter: MainMenuPresenter
) {
    private val data = DataPlaceholder()
    init {
        Log.d("Provider", this.hashCode().toString())
        data.moviesItems.forEach {
            it.categoryIds.addAll(
                listOf(1, 2, 3, 4, 5, 6)
            )
        }
    }

    private fun loadItemsForCategory(categoryId: Int): ItemsDataModel {
        val items = mutableListOf<Item>()
        data.moviesItems.forEach { item ->
            if (item.categoryIds.contains(categoryId))
                items.add(item)
        }
        return ItemsDataModel(items)
    }
    fun loadTestCategories() {
        val categories = mutableListOf<CategoryModel>()
        data.headers.forEach {
            categories.add(
                CategoryModel(it.titleResource, it.headerId, loadItemsForCategory(it.headerId))
            )
        }
        presenter.categoriesLoaded(categories)
    }
}
