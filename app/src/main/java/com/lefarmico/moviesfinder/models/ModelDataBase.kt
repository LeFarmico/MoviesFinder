
package com.lefarmico.moviesfinder.models

import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.Item

interface ModelDataBase {
    val models: Map<Int, ModelItems>
    val modelIds: List<Int>
    fun loadData()
    fun updateData()
    fun updateModelData(modelId: Int)
    fun getAdapters(): List<ItemsPlaceholderAdapter>
    fun getItemsModel(modelId: Int): ModelItems?
    fun getSingleInstanceItems(): List<Item>
}
