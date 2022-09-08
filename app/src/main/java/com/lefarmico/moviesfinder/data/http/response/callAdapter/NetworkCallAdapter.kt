package com.lefarmico.moviesfinder.data.http.response.callAdapter

import com.lefarmico.moviesfinder.data.http.response.NetworkResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkCallAdapter(
    private val resultType: Type
) : CallAdapter<Type, Call<NetworkResponse<Type>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<NetworkResponse<Type>> =
        NetworkCall(call)
}
