package com.lefarmico.moviesfinder.data.http.response.entity

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Error(val exception: Throwable) : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
}
