
package com.lefarmico.moviesfinder.presenters

import com.lefarmico.moviesfinder.models.Item
import com.lefarmico.moviesfinder.models.CategoryModel

interface MainMenuPresenter {
    fun categoriesLoaded(categoryModels: List<CategoryModel>)
    fun showError(textResource: Int)
    fun categoryClicked(category: CategoryModel)
    fun onItemClicked(item: Item)
}