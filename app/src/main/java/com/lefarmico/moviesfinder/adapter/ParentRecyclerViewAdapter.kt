package com.lefarmico.moviesfinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapterDelegates.container.ParentModel
import kotlinx.android.synthetic.main.parent_recycler.view.*

class ParentRecyclerViewAdapter(private val parents : List<ParentModel>) : RecyclerView.Adapter<ParentRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.recycler_child
        val title: TextView = itemView.category_name
    }

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.parent_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = parents[position]
        holder.title.text = parent.categoryName
        val childLayoutManager = LinearLayoutManager(
            holder.recyclerView.context,
            RecyclerView.HORIZONTAL,
            false)

        childLayoutManager.initialPrefetchItemCount = 4

        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = ChildRecyclerViewAdapter(parent.items)
            setRecycledViewPool(viewPool)
        }
    }

    override fun getItemCount(): Int {
        return parents.size
    }
}