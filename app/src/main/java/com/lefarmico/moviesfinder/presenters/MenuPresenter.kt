
package com.lefarmico.moviesfinder.presenters

import com.lefarmico.moviesfinder.data.IHeader
import com.lefarmico.moviesfinder.data.Item
import com.lefarmico.moviesfinder.data.ItemsData
import com.lefarmico.moviesfinder.fargments.MovieFragment
import com.lefarmico.moviesfinder.models.MenuModel

interface MenuPresenter {

    val itemsFragment: MovieFragment
    val menuModel: MenuModel

    fun addItems(items: List<Item>)
    fun addItems(itemsData: ItemsData)
    fun addCategory(title: IHeader)
    fun setItemsData()
}
