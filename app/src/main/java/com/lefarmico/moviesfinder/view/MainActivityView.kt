
package com.lefarmico.moviesfinder.view

import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.MovieItem

interface MainActivityView {
    var interactor: Interactor
    fun launchFragment(fragment: Fragment, tag: String)
    fun launchItemDetails(movieItem: MovieItem)
    fun showError()
}
