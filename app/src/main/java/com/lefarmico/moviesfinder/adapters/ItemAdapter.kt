
package com.lefarmico.moviesfinder.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.ItemBinding
import com.lefarmico.moviesfinder.models.ItemHeader
import com.lefarmico.moviesfinder.private.ApiConstants
import com.squareup.picasso.Picasso

class ItemAdapter(
    private val listener: (ItemHeader) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val items = mutableListOf<ItemHeader>()

    class ViewHolder(itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val poster: ImageView = itemBinding.poster
        val title: TextView = itemBinding.movieTitle

        fun bind(itemHeader: ItemHeader) {
            title.text = itemHeader.title
            Picasso
                .get()
                .load(ApiConstants.IMAGES_URL + "w342" + itemHeader.posterPath)
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
            listener(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(itemsList: List<ItemHeader>) {
        items.addAll(itemsList)
        notifyDataSetChanged()
    }

    fun addItems(itemsList: List<ItemHeader>) {
        items.addAll(itemsList)
        notifyItemRangeInserted(itemsList.size, itemsList.size)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(items.size)
    }
}
