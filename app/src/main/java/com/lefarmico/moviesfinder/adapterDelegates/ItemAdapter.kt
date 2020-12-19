package com.lefarmico.moviesfinder.adapterDelegates

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lefarmico.moviesfinder.DetailsActivity
import com.lefarmico.moviesfinder.adapterDelegates.item.Item
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem

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