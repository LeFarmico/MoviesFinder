package com.lefarmico.moviesfinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.data.appEntity.SearchItem
import com.lefarmico.moviesfinder.data.appEntity.SearchType
import com.lefarmico.moviesfinder.databinding.ItemSearchBinding
import com.lefarmico.moviesfinder.utils.RecyclerViewAdapterWithListener

class SearchAdapter : RecyclerViewAdapterWithListener<SearchItem, RecyclerView.ViewHolder>() {

    override var items: MutableList<SearchItem> = mutableListOf()
    override var onClickEvent: OnClickEvent<SearchItem>? = null

    class RecentlySearchedViewHolder(itemSearchBinding: ItemSearchBinding) : RecyclerView.ViewHolder(itemSearchBinding.root) {
        private val genreText: TextView = itemSearchBinding.genreTextView

        fun bind(searchItem: ItemHeader) {
            genreText.text = searchItem.title
        }
    }
    class SearchViewHolder(itemSearchBinding: ItemSearchBinding) : RecyclerView.ViewHolder(itemSearchBinding.root) {
        private val genreText: TextView = itemSearchBinding.genreTextView

        fun bind(searchItem: ItemHeader) {
            genreText.text = searchItem.title
        }
    }

    override fun onCreateViewHolderWithListener(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            SearchType.RECENTLY_SEARCHED.typeNumber -> {
                RecentlySearchedViewHolder(
                    ItemSearchBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            SearchType.SEARCH.typeNumber -> {
                SearchViewHolder(
                    ItemSearchBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                throw (IllegalArgumentException("Illegal viewType in SearchItem"))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (items[position].viewType) {
            SearchType.RECENTLY_SEARCHED -> {
                val viewHolder = holder as RecentlySearchedViewHolder
                viewHolder.bind(items[position].itemHeader)
            }
            SearchType.SEARCH -> {
                val viewHolder = holder as SearchViewHolder
                viewHolder.bind(items[position].itemHeader)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType.typeNumber
    }
    override fun getItemCount(): Int = items.size

    fun setSearchItems(list: List<ItemHeader>, searchType: SearchType) {
        val searchItemList = mutableListOf<SearchItem>()
        list.forEach { itemHeader ->
            val searchItem = SearchItem(searchType, itemHeader)
            searchItemList.add(searchItem)
        }
        items = searchItemList
        // TODO Change it
        notifyDataSetChanged()
    }
}
