package com.lefarmico.moviesfinder.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.entity.MenuItemType
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.databinding.ItemMenuMoviesBinding
import com.lefarmico.moviesfinder.ui.common.adapter.ItemAdapter
import com.lefarmico.moviesfinder.ui.common.decorator.PaddingItemDecoration

class MenuItemAdapter(
    private val onMovieClick: (MovieBriefData) -> Unit
) : ListAdapter<MenuItem, RecyclerView.ViewHolder>(MenuItemDiffUtil()) {

    class MoviesViewHolder(
        menuMoviesBinding: ItemMenuMoviesBinding
    ) : RecyclerView.ViewHolder(menuMoviesBinding.root) {

        private val header = menuMoviesBinding.headerTitle
        private val moviesListRecycler = menuMoviesBinding.itemsList.also {
            it.addItemDecoration(
                PaddingItemDecoration(
                    leftPd = itemView.context.resources.getDimensionPixelOffset(
                        R.dimen.stnd_margin
                    )
                ),
                0
            )
        }

        fun bind(movies: MenuItem.Movies, onItemClick: (MovieBriefData) -> Unit) {
            val headerText = itemView.context.getString(movies.movieCategoryData.categoryResource)
            header.text = headerText
            moviesListRecycler.adapter = ItemAdapter(onItemClick).apply {
                items = movies.movieBriefDataList
            }
        }
    }

    class MenuItemDiffUtil : DiffUtil.ItemCallback<MenuItem>() {
        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MenuItemType.Movies.typeNumber -> MoviesViewHolder(
                ItemMenuMoviesBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> {
                throw IllegalArgumentException("Incorrect viewType: $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val viewType = getItem(position).menuItemType.typeNumber) {
            MenuItemType.Movies.typeNumber -> {
                val viewHolder = holder as MoviesViewHolder
                val item = getItem(position) as MenuItem.Movies
                viewHolder.bind(item, onMovieClick)
            }
            else -> {
                throw IllegalArgumentException("Incorrect viewType: $viewType")
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).menuItemType.typeNumber
}
