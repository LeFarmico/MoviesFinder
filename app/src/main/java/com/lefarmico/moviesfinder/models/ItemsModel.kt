
package com.lefarmico.moviesfinder.models

import androidx.annotation.NonNull
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.ItemsData

class ItemsModel(
    @NonNull override var itemsData: ItemsData
) : ModelItems {

    init {
        createAdapter()
    }
    override var id: Int = 0
    override lateinit var adapter: ItemsPlaceholderAdapter

    override fun updateItems() {
        // Going to the Data
    }

    override fun createAdapter() {
        val adapter = ItemsPlaceholderAdapter()
        adapter.setNestedItemsData(itemsData)
        this.adapter = adapter
    }
}
