package com.lefarmico.moviesfinder.data.http.response

import retrofit2.HttpException
import retrofit2.Response

fun <T : Any> handleRequest(
    execute: () -> Response<T>
): NetworkResponse<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResponse.Success(body)
        } else {
            NetworkResponse.Error(
                code = response.code(),
                message = response.message()
            )
        }
    } catch (e: HttpException) {
        NetworkResponse.Error(
            code = e.code(),
            message = e.message()
        )
    } catch (e: Throwable) {
        NetworkResponse.Exception(e)
    }
}
