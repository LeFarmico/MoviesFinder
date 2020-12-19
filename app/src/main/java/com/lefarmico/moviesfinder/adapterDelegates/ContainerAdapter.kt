package com.lefarmico.moviesfinder.adapterDelegates

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.moviesfinder.adapterDelegates.container.Container
import com.lefarmico.moviesfinder.adapterDelegates.item.Item

class ContainerAdapter(private val clickListener: OnContainerClickListener): ListDelegationAdapter<List<Container>>() {
    //Создаем делегат для родительского адаптера
    init {
        delegatesManager.addDelegate(ContainerDelegateAdapter(clickListener))
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

    interface OnContainerClickListener{
        fun click(item: Item)
    }
}