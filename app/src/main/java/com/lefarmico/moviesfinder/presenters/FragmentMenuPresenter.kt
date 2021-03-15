
package com.lefarmico.moviesfinder.presenters

import com.lefarmico.moviesfinder.models.CategoryModel

interface FragmentMenuPresenter {
    fun loadCategory(categoryModel: CategoryModel)
    fun categoriesLoaded(categoryModels: List<CategoryModel>)
    fun showError(textResource: Int)
}
