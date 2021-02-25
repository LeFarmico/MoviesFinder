
package com.lefarmico.moviesfinder.view

import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.models.Item

interface MainView {
    fun launchFragment(fragment: Fragment, tag: String)
    fun launchItemDetails(item: Item)
    // TODO : Добавить класс реализации
    fun launchNavigationComponent()
    fun showError()
    fun showLoading()
    fun endLoading()
}
