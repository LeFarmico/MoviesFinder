package com.lefarmico.moviesfinder.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewAdapterWithListener<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    abstract fun onCreateViewHolderWithListener(parent: ViewGroup, viewType: Int): VH
    abstract var items: MutableList<T>
    abstract var onClickEvent: OnClickEvent<T>?

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateViewHolderWithListener(parent, viewType).listen { position, _ ->
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
