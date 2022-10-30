package com.lefarmico.moviesfinder.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R

class HomeLoadStateAdapter : LoadStateAdapter<HomeLoadStateAdapter.HomeLoadStateViewHolder>() {

    class HomeLoadStateViewHolder(
        parent: ViewGroup,
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu_movies_load_state, parent, false)
    ) {

        fun bind(loadState: LoadState) {
            // TODO settings of loadState
        }
    }

    override fun onBindViewHolder(holder: HomeLoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HomeLoadStateViewHolder = HomeLoadStateViewHolder(parent)
}
