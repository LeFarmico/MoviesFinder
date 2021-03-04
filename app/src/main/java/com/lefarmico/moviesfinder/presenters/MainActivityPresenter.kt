package com.lefarmico.moviesfinder.presenters

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.models.ItemHeader
import com.lefarmico.moviesfinder.models.MovieItem

interface MainActivityPresenter {
    val fragmentsMap: MutableMap<String, Fragment>
    fun attachView(view: MainActivity)
    fun launchFragment(fragment: Fragment, tag: String)
    fun launchFragments()
    fun onItemClick(itemHeader: ItemHeader)
    fun showItemDetails(movieItem: MovieItem)
    fun onFabClick()
    fun showError(@StringRes textResource: Int)
    fun onLoaded()
    fun showFragment(fragment: Fragment)
}
