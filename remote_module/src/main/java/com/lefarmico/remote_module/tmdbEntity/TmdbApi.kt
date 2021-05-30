package com.lefarmico.remote_module.tmdbEntity
import com.lefarmico.remote_module.tmdbEntity.preferences.TmdbMovieDetailsResult
import com.lefarmico.remote_module.tmdbEntity.preferences.TmdbMovieListResult
import io.reactivex.rxjava3.core.Observable
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
    ): Observable<TmdbMovieListResult>

    @GET("3/movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("append_to_response") append: String
    ): Observable<TmdbMovieDetailsResult>

    @GET("3/search/movie")
    fun getMovieFromSearch(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Observable<TmdbMovieListResult>
}
