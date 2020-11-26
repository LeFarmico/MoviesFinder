package com.lefarmico.moviesfinder.adapterDelegates

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.moviesfinder.adapterDelegates.item.Item

class ParentAdapter: ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(ParentDelegateAdapter())
    }
    override fun setItems(items: List<Item>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }
}