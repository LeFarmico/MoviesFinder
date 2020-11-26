package com.lefarmico.moviesfinder.adapterDelegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapterDelegates.item.Item

import com.lefarmico.moviesfinder.adapterDelegates.item.ParentModel

class ParentDelegateAdapter: AbsListItemAdapterDelegate<ParentModel, Item, ParentDelegateAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val category: TextView = itemView.findViewById(R.id.category_name)
        var recyclerView: RecyclerView = itemView.findViewById(R.id.recycler_container)
    }

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean {
        return item is ParentModel
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.parent_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(item: ParentModel, holder: ViewHolder, payloads: MutableList<Any>) {
        holder.category.text = item.categoryName
        val childLayoutManager = LinearLayoutManager(
            holder.recyclerView.context,
            RecyclerView.HORIZONTAL,
        false)
        childLayoutManager.initialPrefetchItemCount = 4
        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = MoviesAdapter()
            setRecycledViewPool(viewPool)
        }

    }
}