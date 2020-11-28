package com.lefarmico.moviesfinder.adapterDelegates

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.moviesfinder.adapterDelegates.container.Container

class ContainerAdapter: ListDelegationAdapter<List<Container>>() {
    //Создаем делегат для родительского адаптера
    init {
        delegatesManager.addDelegate(ContainerDelegateAdapter())
    }
    override fun setItems(items: List<Container>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }
}