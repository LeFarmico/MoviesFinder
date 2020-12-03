package com.lefarmico.moviesfinder.adapterDelegates

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.moviesfinder.adapterDelegates.container.Container

class ContainerAdapter: ListDelegationAdapter<List<Container>>() {
    //Создаем делегат для родительского адаптера
    init {
        delegatesManager.addDelegate(ContainerDelegateAdapter())
        delegatesManager.addDelegate(HeaderDelegateAdapter())
        //Заставляем перерисовываться только те объекты, которые изменились
        setHasStableIds(true)
        notifyDataSetChanged()
    }

    override fun setItems(items: List<Container>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

}