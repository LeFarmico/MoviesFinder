
package com.lefarmico.moviesfinder.view

import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.models.Item

interface MainView {
    val fragments: Map<String, Fragment>
    fun launchFragment(fragment: Fragment, tag: String)
    fun launchItemDetails(item: Item)
    // TODO : Добавить класс реализации
    fun launchNavigationComponent()
    fun showError()
}
