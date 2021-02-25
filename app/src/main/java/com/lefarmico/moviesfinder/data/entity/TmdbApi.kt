package com.lefarmico.moviesfinder.data.entity

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("3/movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Call<TmdbResults>

    @GET("3/movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Call<TmdbResults>
}
