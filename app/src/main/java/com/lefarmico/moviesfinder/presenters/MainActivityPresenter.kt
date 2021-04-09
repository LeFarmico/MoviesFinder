package com.lefarmico.moviesfinder.presenters

import androidx.annotation.StringRes
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.models.ItemHeader
import com.lefarmico.moviesfinder.models.MovieItem

interface MainActivityPresenter {
    fun attachView(view: MainActivity)
    fun onItemClick(itemHeader: ItemHeader)
    fun showItemDetails(movieItem: MovieItem)
    fun onFabClick()
    fun showError(@StringRes textResource: Int)
    fun onLoaded()
}
