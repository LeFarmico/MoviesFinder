
package com.lefarmico.moviesfinder.view

import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.models.MovieItem

interface MainActivityView {
    var interactor: Interactor
    fun launchFragment(fragment: Fragment, tag: String)
    fun showFragment(fragment: Fragment)
    fun hideFragment(fragment: Fragment)
    fun launchItemDetails(movieItem: MovieItem)
    fun launchFabMenu()
    fun showError()
    fun showLoading()
    fun endLoading()
}