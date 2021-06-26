package com.lefarmico.moviesfinder.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AdapterOnWithListener<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun onCreateViewHolderWithListener(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
//    abstract val listener: (position: Int, type: Int) -> Unit
    abstract var items: MutableList<T>
    abstract var onClickEvent: OnClickEvent<T>?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateViewHolderWithListener(parent, viewType).listen { position, _ ->
//            listener(position, type)
            onClickCallback(items[position], onClickEvent)
        }
    }
    private fun onClickCallback(item: T, event: OnClickEvent<T>?) {
        if (event != null) {
            event.onClick(item)
        } else {
            return
        }
    }

    fun setOnClickEvent(event: (T) -> Unit) {
        onClickEvent = object : OnClickEvent<T> {
            override fun onClick(item: T) {
                event(item)
            }
        }
    }

    interface OnClickEvent<T> {
        fun onClick(item: T)
    }
}
