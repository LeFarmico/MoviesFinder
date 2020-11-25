package com.lefarmico.moviesfinder.adapterDelegates

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.moviesfinder.adapterDelegates.item.Item

class MoviesAdapter: ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(MovieDelegateAdapter()) //movieDelegateAdaptor
    }

    override fun setItems(items: List<Item>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }
}