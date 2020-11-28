package com.lefarmico.moviesfinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem
import kotlinx.android.synthetic.main.child_recycler.view.*

class ChildRecyclerViewAdapter(private val children : List<MovieItem>) : RecyclerView.Adapter<ChildRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.poster
        val title: TextView = itemView.movie_title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.child_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = children[position]
        holder.poster.setImageResource(child.poster)
        holder.title.text = child.title
    }

    override fun getItemCount(): Int {
        return children.size
    }
}