package com.lefarmico.moviesfinder.data.http.response.entity
import com.google.gson.annotations.SerializedName

data class TmdbMovieListResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val tmdbMovie: List<TmdbMovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
