package com.lefarmico.moviesfinder.adapterDelegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.moviesfinder.MainActivity
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapterDelegates.item.Item
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem
import kotlinx.android.synthetic.main.child_recycler.view.*

class ItemDelegateAdapter(): AbsListItemAdapterDelegate<MovieItem, Item, ItemDelegateAdapter.ViewHolder>() {
    //Создаем холдер, для связи с view из Item
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val poster: ImageView = itemView.poster
        val title: TextView = itemView.movie_title
    }

    //Определяем тип Item
    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean {
        return item is MovieItem
    }


    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.child_recycler, parent, false))
    }

    override fun onBindViewHolder(item: MovieItem, holder: ViewHolder, payloads: MutableList<Any>) {
        holder.poster.setImageResource(item.poster)
        holder.title.text = item.title

        setOnItemClick(holder)
    }

    private fun setOnItemClick(holder: ViewHolder) {
        holder.poster.setOnClickListener {
            Toast.makeText(it.context, "push", Toast.LENGTH_SHORT).show()
        }
    }
}