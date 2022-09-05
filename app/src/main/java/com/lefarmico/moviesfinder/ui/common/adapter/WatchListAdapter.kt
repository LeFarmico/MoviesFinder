package com.lefarmico.moviesfinder.ui.common.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.databinding.ItemWatchListRecyclerBinding
import com.lefarmico.moviesfinder.private.Private
import com.lefarmico.moviesfinder.ui.common.widget.RatingView
import com.squareup.picasso.Picasso

class WatchListAdapter(
    private val onClick: (MovieBriefData) -> Unit
) : RecyclerView.Adapter<WatchListAdapter.ViewHolder>() {

    private var items: List<MovieBriefData> = emptyList()

    class WatchListDiffUtil(
        private val oldList: List<MovieBriefData>,
        private val newList: List<MovieBriefData>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] === newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    class ViewHolder(itemBinding: ItemWatchListRecyclerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val poster: ImageView = itemBinding.poster
        val title: TextView = itemBinding.title
        val description: TextView = itemBinding.description
        val rating: RatingView = itemBinding.movieRate

        fun bind(movieBriefData: MovieBriefData) {
            title.text = movieBriefData.title
            description.text = movieBriefData.description
            rating.setRatingValue(movieBriefData.rating)
            Picasso
                .get()
                .load(Private.IMAGES_URL + "w342" + movieBriefData.posterPath)
                .fit()
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemWatchListRecyclerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TAG", "bind, position = $position")
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<MovieBriefData>) {
        val oldField = this.items
        this.items = items
        val diffCallback = WatchListDiffUtil(oldField, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }
}
