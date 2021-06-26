package com.lefarmico.moviesfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.data.appEntity.SearchItem
import com.lefarmico.moviesfinder.data.appEntity.SearchType
import com.lefarmico.moviesfinder.databinding.ItemSearchBinding
import com.lefarmico.moviesfinder.utils.IAdapterOnClickListener
import com.lefarmico.moviesfinder.utils.IAdapterOnClickListener.*
import com.lefarmico.moviesfinder.utils.listen
import java.lang.IllegalArgumentException

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IAdapterOnClickListener {

    private var searchItems: List<SearchItem> = mutableListOf()
    override var onClickEvent: OnClickEvent? = null

    class RecentlySearchedViewHolder(itemSearchBinding: ItemSearchBinding) : RecyclerView.ViewHolder(itemSearchBinding.root) {
        private val genreText: TextView = itemSearchBinding.genreTextView
        private val searchImage: ImageView = itemSearchBinding.searchImage
        private val searchIcon = R.drawable.ic_baseline_youtube_searched_for_24

        fun bind(searchItem: ItemHeader) {
            genreText.text = searchItem.title
            searchImage.setImageResource(searchIcon)
        }
    }
    class SearchViewHolder(itemSearchBinding: ItemSearchBinding) : RecyclerView.ViewHolder(itemSearchBinding.root) {
        private val genreText: TextView = itemSearchBinding.genreTextView
        private val searchImage: ImageView = itemSearchBinding.searchImage

        private val searchIcon = R.drawable.ic_baseline_search_24
        fun bind(searchItem: ItemHeader) {
            genreText.text = searchItem.title
            searchImage.setImageResource(searchIcon)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SearchType.RECENTLY_SEARCHED.typeNumber -> {
                RecentlySearchedViewHolder(
                    ItemSearchBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                ).listen { position, _ ->
                    onClickCallback(searchItems[position], onClickEvent)
                }
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
        when (searchItems[position].viewType) {
            SearchType.RECENTLY_SEARCHED -> {
                val viewHolder = holder as RecentlySearchedViewHolder
                viewHolder.bind(searchItems[position].itemHeader)
            }
            SearchType.SEARCH -> {
                val viewHolder = holder as SearchViewHolder
                viewHolder.bind(searchItems[position].itemHeader)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return searchItems[position].viewType.typeNumber
    }
    override fun getItemCount(): Int = searchItems.size

    fun setSearchItems(list: List<ItemHeader>, searchType: SearchType) {
        val searchItemList = mutableListOf<SearchItem>()
        list.forEach { itemHeader ->
            val searchItem = SearchItem(searchType, itemHeader)
            searchItemList.add(searchItem)
        }
        searchItems = searchItemList
        notifyDataSetChanged()
    }
}
