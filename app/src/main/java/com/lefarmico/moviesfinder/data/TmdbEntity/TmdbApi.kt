package com.lefarmico.moviesfinder.data.TmdbEntity

import com.lefarmico.moviesfinder.data.TmdbEntity.preferences.TmdbMovieDetailsResult
import com.lefarmico.moviesfinder.data.TmdbEntity.preferences.TmdbMovieListResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("3/movie/{category}")
    fun getMovies(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Call<TmdbMovieListResult>

    @GET("3/movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
    ): Call<TmdbMovieDetailsResult>

    @GET("3/movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("append_to_response") append: String
    ): Call<TmdbMovieDetailsResult>
}
