package com.lefarmico.moviesfinder.models

import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.ItemsData

interface ItemsModelInterface {
    var adapter: ItemsPlaceholderAdapter
    var id: Int
    var itemsData: ItemsData
    fun setItems(itemsData: ItemsData)
    fun getItems(): ItemsData
    fun updateItems()
    fun createAdapter()
}