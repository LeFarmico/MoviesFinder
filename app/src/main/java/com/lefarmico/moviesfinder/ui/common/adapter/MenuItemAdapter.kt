package com.lefarmico.moviesfinder.ui.common.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.entity.MenuItemType
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.databinding.ItemMenuMoviesBinding
import com.lefarmico.moviesfinder.ui.common.decorator.PaddingItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MenuItemAdapter(
    parentJob: Job,
    private val onMovieClick: (MovieBriefData) -> Unit,
    private val onErrorAction: (Throwable) -> Unit
) : ListAdapter<MenuItem, RecyclerView.ViewHolder>(MenuItemDiffUtil()) {

    private val job = Job(parentJob).apply {
        invokeOnCompletion {
            Log.d(TAG, "the Job have been completed.")
        }
    }

    private val scope = CoroutineScope(Dispatchers.Default + job)

    class MoviesViewHolder(
        menuMoviesBinding: ItemMenuMoviesBinding
    ) : RecyclerView.ViewHolder(menuMoviesBinding.root) {

        private val loadView = menuMoviesBinding.loadState
        private val root = menuMoviesBinding.root
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

        fun bind(
            coroutineScope: CoroutineScope,
            movies: MenuItem.Movies,
            onItemClick: (MovieBriefData) -> Unit,
            onErrorAction: (Throwable) -> Unit
        ) {
            val headerText = itemView.context.getString(movies.movieCategoryData.categoryResource)
            header.text = headerText
            moviesListRecycler.adapter = MovieBriefPagingAdapter(onItemClick).apply {
                addLoadStateListener { loadState ->

                    if (loadState.refresh is LoadState.Loading) {
                        loadView.root.visibility = View.VISIBLE
                        header.visibility = View.GONE
                        moviesListRecycler.visibility = View.GONE
                    } else {
                        loadView.root.visibility = View.GONE
                        header.visibility = View.VISIBLE
                        moviesListRecycler.visibility = View.VISIBLE

                        // getting the error
                        val error = when {
                            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                            else -> null
                        }
                        error?.let {
                            loadView.root.visibility = View.GONE
                            header.visibility = View.GONE
                            moviesListRecycler.visibility = View.GONE
                            onErrorAction(it.error)
                        }
                    }
                }
                coroutineScope.launch {
                    submitData(movies.movieBriefDataList)
                }
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
                viewHolder.bind(scope, item, onMovieClick, onErrorAction)
            }
            else -> {
                throw IllegalArgumentException("Incorrect viewType: $viewType")
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).menuItemType.typeNumber

    companion object {
        const val TAG = "MenuItemAdapter"
    }
}
