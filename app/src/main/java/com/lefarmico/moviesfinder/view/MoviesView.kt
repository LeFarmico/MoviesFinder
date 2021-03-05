
package com.lefarmico.moviesfinder.view

import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.models.ItemHeader

interface MoviesView {
    fun showCatalog(adapter: ConcatAdapter)
    fun showError()
    fun showEmptyCatalog()
    fun onItemClicked(itemHeader: ItemHeader)
    fun onStartLoading()
}
