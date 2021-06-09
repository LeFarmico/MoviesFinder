package com.lefarmico.moviesfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.databinding.ItemSearchBinding

class SearchAdapter(
    private val click: (ItemHeader) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private lateinit var searchItems: Pair<SearchType, List<ItemHeader>>

    class ViewHolder(itemSearchBinding: ItemSearchBinding) : RecyclerView.ViewHolder(itemSearchBinding.root) {
        private val genreText: TextView = itemSearchBinding.genreTextView
        private val searchImage: ImageView = itemSearchBinding.searchImage

        fun bind(searchItem: ItemHeader, @DrawableRes imageRes: Int, clickCallback: () -> Unit) {
            genreText.text = searchItem.title
            searchImage.setImageResource(imageRes)
            genreText.setOnClickListener {
                clickCallback()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (searchItems.first) {
            SearchType.RECENTLY_SEARCHED -> {
                holder.bind(searchItems.second[position], R.drawable.ic_baseline_youtube_searched_for_24) {
                    click(searchItems.second[position])
                }
            }
            SearchType.SEARCH -> {
                holder.bind(searchItems.second[position], R.drawable.ic_round_search_24) {
                    click(searchItems.second[position])
                }
            }
        }
    }

    override fun getItemCount(): Int = searchItems.second.size

    fun setLastSearchItems(list: List<ItemHeader>) {
        val data = Pair(SearchType.RECENTLY_SEARCHED, list.reversed())
        searchItems = data
        notifyDataSetChanged()
    }
    fun setSearchItems(list: List<ItemHeader>) {
        val data = Pair(SearchType.SEARCH, list)
        searchItems = data
        notifyDataSetChanged()
    }

    enum class SearchType {
        RECENTLY_SEARCHED, SEARCH
    }
}
