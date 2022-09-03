
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
import com.lefarmico.moviesfinder.databinding.ItemBinding
import com.lefarmico.moviesfinder.private.ApiConstants
import com.squareup.picasso.Picasso

class ItemAdapter(
    private val onClick: (MovieBriefData) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    var items: List<MovieBriefData> = emptyList()
        set(value) {
            val oldField = field
            field = value
            val diffCallback = ItemDiffUtil(oldField, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    class ItemDiffUtil(
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

    class ViewHolder(itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val poster: ImageView = itemBinding.poster
        val title: TextView = itemBinding.movieTitle

        fun bind(movieBriefData: MovieBriefData) {
            title.text = movieBriefData.title
            Picasso
                .get()
                .load(ApiConstants.IMAGES_URL + "w342" + movieBriefData.posterPath)
                .fit()
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(poster)
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
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}
