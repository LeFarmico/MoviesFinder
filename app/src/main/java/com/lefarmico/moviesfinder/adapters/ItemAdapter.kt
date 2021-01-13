
package com.lefarmico.moviesfinder.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.data.Item
import com.lefarmico.moviesfinder.databinding.ItemBinding

class ItemAdapter() : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    // TODO : Inject to model
    private val items = mutableListOf<Item>()

    class ViewHolder(itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val poster: ImageView = itemBinding.poster
        val title: TextView = itemBinding.movieTitle

        fun bind(item: Item) {
            poster.setImageResource(item.posterId)
            title.text = item.title
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
    }

    override fun getItemCount(): Int = items.size

    fun addItem(item: Item) {
        items.add(item)
        notifyItemChanged(items.size - 1)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(items.size)
    }
    fun setItems(itemsList: List<Item>) {
        items.addAll(itemsList)
        notifyDataSetChanged()
    }
}
