package com.lefarmico.moviesfinder.utils

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

fun <T> MutableLiveData<List<T>>.add(item: T) {
    val updatedItems = this.value as ArrayList
    updatedItems.add(item)
    this.value = updatedItems
}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(bindingAdapterPosition, itemViewType)
    }
    return this
}
