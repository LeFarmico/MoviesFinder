package com.lefarmico.moviesfinder.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.entity.SearchType
import com.lefarmico.moviesfinder.data.entity.SearchedItemData
import com.lefarmico.moviesfinder.databinding.ItemSearchBinding

class SearchAdapter(
    private val onItemClick: (SearchedItemData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<SearchedItemData> = emptyList()
        set(value) {
            val oldField = field
            field = value
            val diffCallback = SearchDiffUtil(oldField, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    class SearchDiffUtil(
        private val oldList: List<SearchedItemData>,
        private val newList: List<SearchedItemData>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] === newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    class RecentlySearchedViewHolder(itemSearchBinding: ItemSearchBinding) : RecyclerView.ViewHolder(itemSearchBinding.root) {
        private val genreText: TextView = itemSearchBinding.genreTextView

        fun bind(searchItem: MovieBriefData) {
            genreText.text = searchItem.title
        }
    }
    class SearchViewHolder(itemSearchBinding: ItemSearchBinding) : RecyclerView.ViewHolder(itemSearchBinding.root) {
        private val genreText: TextView = itemSearchBinding.genreTextView

        fun bind(searchItem: MovieBriefData) {
            genreText.text = searchItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SearchType.RecentlySearch.typeNumber -> {
                RecentlySearchedViewHolder(
                    ItemSearchBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            SearchType.Search.typeNumber -> {
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
            SearchType.RecentlySearch -> {
                val viewHolder = holder as RecentlySearchedViewHolder
                viewHolder.bind(items[position].movieBriefData)
                viewHolder.itemView.setOnClickListener {
                    onItemClick(items[position])
                }
            }
            SearchType.Search -> {
                val viewHolder = holder as SearchViewHolder
                viewHolder.bind(items[position].movieBriefData)
                viewHolder.itemView.setOnClickListener {
                    onItemClick(items[position])
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType.typeNumber
    }
    override fun getItemCount(): Int = items.size
}
