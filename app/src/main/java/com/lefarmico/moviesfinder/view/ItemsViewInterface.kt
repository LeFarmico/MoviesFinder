package com.lefarmico.moviesfinder.view

import com.lefarmico.moviesfinder.data.Item

interface ItemsViewInterface {
    var id: Int
    fun setItems(itemsList: List<Item>)
    fun updateItems()
    fun removeItem(position: Int)
}