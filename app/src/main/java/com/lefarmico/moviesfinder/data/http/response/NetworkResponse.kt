package com.lefarmico.moviesfinder.data.http.response

sealed interface NetworkResponse<T : Any> {

    data class Success<T : Any>(val data: T) : NetworkResponse<T>
    data class Error<T : Any>(val code: Int, val message: String) : NetworkResponse<T>
    data class Exception<T : Any>(val throwable: Throwable) : NetworkResponse<T>
}

suspend fun <T : Any> NetworkResponse<T>.onSuccess(
    action: suspend (T) -> Unit
): NetworkResponse<T> = apply {
    if (this is NetworkResponse.Success<T>) {
        // BUG?
        action(data)
    }
}

suspend fun <T : Any> NetworkResponse<T>.onError(
    action: suspend (code: Int, message: String?) -> Unit
): NetworkResponse<T> = apply {
    if (this is NetworkResponse.Error<T>) {
        action(code, message)
    }
}

suspend fun <T : Any> NetworkResponse<T>.onException(
    action: suspend (e: Throwable) -> Unit
): NetworkResponse<T> = apply {
    if (this is NetworkResponse.Exception<T>) {
        action(throwable)
    }
}
