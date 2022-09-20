package com.lefarmico.moviesfinder.data.http.request
import com.lefarmico.moviesfinder.data.http.response.NetworkResponse
import com.lefarmico.moviesfinder.data.http.response.entity.TmdbMovieDetailsResult
import com.lefarmico.moviesfinder.data.http.response.entity.TmdbMovieListResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("3/movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): NetworkResponse<TmdbMovieListResult>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("append_to_response") append: String? = null
    ): NetworkResponse<TmdbMovieDetailsResult>

    @GET("3/search/movie")
    suspend fun getMovieFromSearch(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): NetworkResponse<TmdbMovieListResult>

    @GET("3/movie/{movie_id}/recommendations")
    suspend fun getRecommendations(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Path("movie_id") movieId: Int
    ): NetworkResponse<TmdbMovieListResult>
}
