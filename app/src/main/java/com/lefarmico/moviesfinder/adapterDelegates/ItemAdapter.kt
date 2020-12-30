package com.lefarmico.moviesfinder.adapterDelegates

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.moviesfinder.adapterDelegates.item.Item

class ItemAdapter(private val clickListener: OnItemClickListener): ListDelegationAdapter<List<Item>>() {
    //Создаем delegate для Ячеек Родительского адаптера
    init {
        delegatesManager.addDelegate(ItemDelegateAdapter(clickListener)) //movieDelegateAdaptor
    }

    override fun setItems(items: List<Item>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }
    interface OnItemClickListener{
        fun click(item: Item)
    }
}