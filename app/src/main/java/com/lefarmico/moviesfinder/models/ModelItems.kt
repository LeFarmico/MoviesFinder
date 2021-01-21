
package com.lefarmico.moviesfinder.models

import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.ItemsData

interface ModelItems {
    var adapter: ItemsPlaceholderAdapter
    var id: Int
    var itemsData: ItemsData
    fun updateItems()
    fun createAdapter()
}