package com.lefarmico.moviesfinder.adapterDelegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapterDelegates.container.Container
import com.lefarmico.moviesfinder.adapterDelegates.container.HeaderModel
import kotlinx.android.synthetic.main.header_recycler.view.*

class HeaderDelegateAdapter: AbsListItemAdapterDelegate<HeaderModel, Container, HeaderDelegateAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.header_title)
    }

    override fun isForViewType(item: Container, items: MutableList<Container>, position: Int): Boolean {
        return item is HeaderModel
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.header_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(item: HeaderModel, holder: ViewHolder, payloads: MutableList<Any>) {
        holder.title.text = item.title
    }
}