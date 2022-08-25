package com.lefarmico.moviesfinder.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.customViews.RatingView
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.databinding.ItemWatchListRecyclerBinding
import com.lefarmico.moviesfinder.private.ApiConstants
import com.lefarmico.moviesfinder.utils.RecyclerViewAdapterWithListener
import com.squareup.picasso.Picasso

class WatchListRecyclerViewAdapter : RecyclerViewAdapterWithListener<ItemHeader, WatchListRecyclerViewAdapter.ViewHolder>() {

    override var items = mutableListOf<ItemHeader>()

    override var onClickEvent: OnClickEvent<ItemHeader>? = null

    class ViewHolder(itemBinding: ItemWatchListRecyclerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val poster: ImageView = itemBinding.poster
        val title: TextView = itemBinding.title
        val description: TextView = itemBinding.description
        val rating: RatingView = itemBinding.movieRate

        fun bind(itemHeader: ItemHeader) {
            title.text = itemHeader.title
            description.text = itemHeader.description
            rating.setRatingValue(itemHeader.rating)
            Picasso
                .get()
                .load(ApiConstants.IMAGES_URL + "w342" + itemHeader.posterPath)
                .fit()
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(poster)
        }
    }
    override fun onCreateViewHolderWithListener(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inlfater = LayoutInflater.from(parent.context)
        val binding = ItemWatchListRecyclerBinding.inflate(inlfater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TAG", "bind, position = $position")
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItemHeaders(itemsList: List<ItemHeader>) {
        items.addAll(itemsList)
        // TODO Change it
        notifyDataSetChanged()
    }

    fun updateItems(itemsList: List<ItemHeader>) {
        val updateList = mutableListOf<ItemHeader>()
        updateList.addAll(itemsList)
        items = updateList
        // TODO Change it
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
