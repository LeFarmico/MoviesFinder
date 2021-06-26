package com.lefarmico.moviesfinder.adapters

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
import com.lefarmico.moviesfinder.utils.listen
import com.squareup.picasso.Picasso

class WatchListAdapter : RecyclerView.Adapter<WatchListAdapter.ViewHolder>() {

    private var items = mutableListOf<ItemHeader>()

    private lateinit var onClickEvent: OnClickEvent

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inlfater = LayoutInflater.from(parent.context)
        val binding = ItemWatchListRecyclerBinding.inflate(inlfater, parent, false)
        return ViewHolder(binding).listen { position, _ ->
            onClickCallback(items[position], onClickEvent)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TAG", "bind, position = $position")
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(itemsList: List<ItemHeader>) {
        items.addAll(itemsList)
        notifyDataSetChanged()
    }

    fun updateItems(itemsList: List<ItemHeader>) {
        val updateList = mutableListOf<ItemHeader>()
        updateList.addAll(itemsList)
        items = updateList
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

    private fun onClickCallback(itemHeader: ItemHeader, event: OnClickEvent?) {
        if (event != null) {
            event.onClick(itemHeader)
        } else {
            return
        }
    }

    fun setOnClickEvent(event: (ItemHeader) -> Unit) {
        onClickEvent = object : OnClickEvent {
            override fun onClick(itemHeader: ItemHeader) {
                event(itemHeader)
            }
        }
    }

    interface OnClickEvent {
        fun onClick(itemHeader: ItemHeader)
    }
}
