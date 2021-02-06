
package com.lefarmico.moviesfinder.view

import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.models.Item

interface MoviesView {
    fun showCatalog(adapter: ConcatAdapter)
    fun showError()
    fun showEmptyCatalog()
    fun onItemClicked(item: Item)
    fun onStartLoading()
}