package com.lefarmico.moviesfinder.adapterDelegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapterDelegates.item.Item
import com.lefarmico.moviesfinder.adapterDelegates.item.Movie

class MovieDelegateAdapter: AbsListItemAdapterDelegate<Movie, Item, MovieDelegateAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val poster = itemView.findViewById<ImageView>(R.id.poster)
        val title = itemView.findViewById<TextView>(R.id.movie_title)
    }

    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean {
        return item is Movie
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))
    }

    override fun onBindViewHolder(item: Movie, holder: ViewHolder, payloads: MutableList<Any>) {
        holder.poster.setImageResource(item.poster)
        holder.title.text = item.title
    }
}