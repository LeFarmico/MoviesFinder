package com.lefarmico.moviesfinder.data.http.response.callAdapter

import com.lefarmico.moviesfinder.data.http.response.NetworkResponse
import com.lefarmico.moviesfinder.data.http.response.handleRequest
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkCall<T : Any> (
    private val proxy: Call<T>
) : Call<NetworkResponse<T>> {

    override fun enqueue(callback: Callback<NetworkResponse<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResponse = handleRequest { response }
                callback.onResponse(this@NetworkCall, Response.success(networkResponse))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResponse = NetworkResponse.Exception<T>(t)
                callback.onResponse(this@NetworkCall, Response.success(networkResponse))
            }
        })
    }

    override fun execute(): Response<NetworkResponse<T>> = throw NotImplementedError()
    override fun clone(): Call<NetworkResponse<T>> = NetworkCall(proxy.clone())
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun cancel() { proxy.cancel() }
}
