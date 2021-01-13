
package com.lefarmico.moviesfinder.models

import com.lefarmico.moviesfinder.data.IHeader
import com.lefarmico.moviesfinder.data.Item
import com.lefarmico.moviesfinder.data.ItemsData

interface MenuModel {
    val itemsGroup: Map<IHeader, ItemsData>
    fun setItems()
    fun getItem(titleName: String, position: Int): Item
    fun getItemsByTitle(title: String): List<Item>
}
