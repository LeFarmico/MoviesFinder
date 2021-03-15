
package com.lefarmico.moviesfinder.view

import androidx.recyclerview.widget.ConcatAdapter

interface MoviesView {
    fun showCatalog(adapter: ConcatAdapter)
    fun showError()
    fun showEmptyCatalog()
    fun onStartLoading()
}
