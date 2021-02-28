package com.lefarmico.moviesfinder.data.entity

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("3/movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Call<TmdbMovieListResultsResults>

    @GET("3/movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Call<TmdbMovieListResultsResults>

    @GET("3/movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Call<TmdbMovieListResultsResults>

    @GET("3/movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Call<TmdbMovieListResultsResults>

    @GET("3/movie/{movie_id}")
    fun getMovieDetails(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Path("movie_id") movieId: Int
    ): Call<TmdbMovieDetailsResults>
}
