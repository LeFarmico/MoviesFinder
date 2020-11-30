package com.lefarmico.moviesfinder.adapterDelegates

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.moviesfinder.adapterDelegates.item.Item

class ItemAdapter: ListDelegationAdapter<List<Item>>() {
    //Создаем delegate для Ячеек Родительского адаптера
    init {
        delegatesManager.addDelegate(ItemDelegateAdapter()) //movieDelegateAdaptor

    }

    override fun setItems(items: List<Item>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }

}