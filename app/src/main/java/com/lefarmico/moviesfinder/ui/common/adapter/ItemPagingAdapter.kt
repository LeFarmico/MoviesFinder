
package com.lefarmico.moviesfinder.ui.common.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.databinding.ItemBinding
import com.lefarmico.moviesfinder.private.Private
import com.squareup.picasso.Picasso

class ItemPagingAdapter(
    private val onClick: (MovieBriefData) -> Unit
) : PagingDataAdapter<MovieBriefData, ItemPagingAdapter.ViewHolder>(ItemDiffUtil()) {

    class ItemDiffUtil : DiffUtil.ItemCallback<MovieBriefData>() {
        override fun areItemsTheSame(oldItem: MovieBriefData, newItem: MovieBriefData): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieBriefData, newItem: MovieBriefData): Boolean =
            oldItem == newItem
    }

    class ViewHolder(itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val poster: ImageView = itemBinding.poster
        val title: TextView = itemBinding.movieTitle

        fun bind(movieBriefData: MovieBriefData?) {
            movieBriefData?.let {
                title.text = movieBriefData.title
                Picasso
                    .get()
                    .load(Private.IMAGES_URL + "w342" + movieBriefData.posterPath)
                    .fit()
                    .error(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(poster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TAG", "bind, position = $position")
        holder.bind(getItem(position))
        if (getItem(position) != null) {
            holder.itemView.setOnClickListener {
                onClick(getItem(position)!!)
            }
        }
    }
}
