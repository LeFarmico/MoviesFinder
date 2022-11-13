package com.lefarmico.moviesfinder.data.http.response.entity
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TmdbMovieListResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val tmdbMovie: List<TmdbMovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) : Parcelable
