
package com.lefarmico.moviesfinder.view

import com.lefarmico.moviesfinder.data.appEntity.Category

interface MoviesView {
    fun showCatalog(categories: List<Category>)
    fun showError()
    fun showEmptyCatalog()
    fun onStartLoading()
}
