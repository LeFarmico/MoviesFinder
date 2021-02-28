
package com.lefarmico.moviesfinder.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.lefarmico.moviesfinder.models.Item

interface MainView {
    val fragmentManager: FragmentManager
    fun launchFragment(fragment: Fragment, tag: String)
    fun launchItemDetails(item: Item)
    fun launchFabMenu()
    fun showError()
    fun showLoading()
    fun endLoading()
}
