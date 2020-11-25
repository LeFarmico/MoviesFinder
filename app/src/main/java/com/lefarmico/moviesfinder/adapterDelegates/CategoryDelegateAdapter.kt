package com.lefarmico.moviesfinder.adapterDelegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapterDelegates.item.Item

import com.lefarmico.moviesfinder.adapterDelegates.item.MoviesCategory

class CategoryDelegateAdapter: AbsListItemAdapterDelegate<MoviesCategory, Item, CategoryDelegateAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val category: TextView = itemView.findViewById<TextView>(R.id.category_name)
        var recyclerView: RecyclerView = itemView.findViewById<RecyclerView>(R.id.movies_horizontal_container)


//        adapter.items = arrayListOf(items))
    }

    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean {
        return item is MoviesCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup): CategoryDelegateAdapter.ViewHolder {
        return CategoryDelegateAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_line_bar_horizontal, parent, false)
        )
    }

    override fun onBindViewHolder(item: MoviesCategory, holder: ViewHolder, payloads: MutableList<Any>) {
        holder.category.text = item.categoryName
        val adapter = MoviesAdapter()
        adapter.items = item.items
        holder.recyclerView.adapter = adapter

    }
}