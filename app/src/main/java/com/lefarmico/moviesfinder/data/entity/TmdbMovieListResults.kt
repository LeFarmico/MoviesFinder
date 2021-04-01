package com.lefarmico.moviesfinder.data.entity

import com.google.gson.annotations.SerializedName

data class TmdbMovieListResults(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val tmdbMovie: List<TmdbMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
