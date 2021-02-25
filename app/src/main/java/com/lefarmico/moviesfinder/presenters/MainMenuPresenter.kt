
package com.lefarmico.moviesfinder.presenters

import com.lefarmico.moviesfinder.models.CategoryModel
import com.lefarmico.moviesfinder.models.Item

interface MainMenuPresenter {
    fun loadCategory(categoryModel: CategoryModel)
    fun categoriesLoaded(categoryModels: List<CategoryModel>)
    fun showError(textResource: Int)
    fun categoryClicked(category: CategoryModel)
    fun onItemClicked(item: Item)
}
