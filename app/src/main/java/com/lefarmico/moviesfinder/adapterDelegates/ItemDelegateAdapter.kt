package com.lefarmico.moviesfinder.adapterDelegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.moviesfinder.*
import com.lefarmico.moviesfinder.adapterDelegates.item.Item
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem
import kotlinx.android.synthetic.main.child_recycler.view.*

class ItemDelegateAdapter(private val clickListenerMovie: OnMovieItemClickListener): AbsListItemAdapterDelegate<MovieItem, Item, ItemDelegateAdapter.ViewHolder>() {
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
        holder.poster.setOnClickListener {
            clickListenerMovie.click(item)
        }
    }
    interface OnMovieItemClickListener{
        fun click(movie: MovieItem)
    }
}