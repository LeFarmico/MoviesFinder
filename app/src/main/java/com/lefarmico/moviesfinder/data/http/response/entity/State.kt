package com.lefarmico.moviesfinder.data.http.response.entity

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Error(val exception: Throwable) : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
}

suspend fun <T : Any> State<T>.onSuccess(
    action: suspend (T) -> Unit
): State<T> = apply {
    if (this is State.Success<T>) {
        action(data)
    }
}

suspend fun <T : Any> State<T>.onLoading(
    action: suspend () -> Unit
): State<T> = apply {
    if (this is State.Loading) {
        action()
    }
}

suspend fun <T : Any> State<T>.onError(
    action: suspend (e: Throwable) -> Unit
): State<T> = apply {
    if (this is State.Error) {
        action(exception)
    }
}
