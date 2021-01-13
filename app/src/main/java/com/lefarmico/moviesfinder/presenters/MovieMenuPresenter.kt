
package com.lefarmico.moviesfinder.presenters

import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.IHeader
import com.lefarmico.moviesfinder.data.Item
import com.lefarmico.moviesfinder.data.ItemsData
import com.lefarmico.moviesfinder.fargments.MovieFragment
import com.lefarmico.moviesfinder.models.MovieMenuModel

class MovieMenuPresenter(
    override val itemsFragment: MovieFragment,
    override val menuModel: MovieMenuModel
) : MenuPresenter {

    override fun addItems(items: List<Item>) {
        // TODO : Убрать в MovieMenuModel
        val adapter = ItemsPlaceholderAdapter().apply {
            setItems(ItemsData(items))
        }

        (itemsFragment.recyclerView.adapter as ConcatAdapter).apply {
            val oldSize = this.itemCount
            addAdapter(adapter)
            notifyItemRangeInserted(oldSize, itemCount)
        }
    }

    override fun addItems(itemsData: ItemsData) {
        // Inject
        val adapter = ItemsPlaceholderAdapter().apply {
            setItems(itemsData)
        }

        (itemsFragment.recyclerView.adapter as ConcatAdapter).apply {
            val oldSize = this.itemCount
            addAdapter(adapter)
            notifyItemRangeInserted(oldSize, itemCount)
        }
    }

    override fun addCategory(title: IHeader) {
        val adapter = HeaderAdapter()
        adapter.addItem(title.title)
        (itemsFragment.recyclerView.adapter as ConcatAdapter).apply {
            val oldSize = this.itemCount
            addAdapter(adapter)
            notifyItemRangeInserted(oldSize, itemCount)
        }
    }

    override fun setItemsData() {
        menuModel.setItems()
        menuModel.itemsGroup.forEach { (header, itemsData) ->
            addCategory(header)
            addItems(itemsData)
        }
    }
}
