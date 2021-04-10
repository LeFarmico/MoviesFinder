
package com.lefarmico.moviesfinder.presenters

import com.lefarmico.moviesfinder.data.entity.appEntity.Category

interface FragmentMenuPresenter {
    fun loadCategory(category: Category)
    fun categoriesLoaded(categories: List<Category>)
    fun showError(textResource: Int)
}
