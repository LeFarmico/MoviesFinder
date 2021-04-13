package com.lefarmico.moviesfinder.utils

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<List<T>>.add(item: T) {
    val updatedItems = this.value as ArrayList
    updatedItems.add(item)
    this.value = updatedItems
}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}
