package com.lefarmico.moviesfinder.data.http.response.entity

import com.google.gson.annotations.SerializedName
import com.lefarmico.moviesfinder.data.http.response.entity.details.TmdbGenre
import com.lefarmico.moviesfinder.data.http.response.entity.details.TmdbProductionCompany
import com.lefarmico.moviesfinder.data.http.response.entity.details.TmdbProductionCountry
import com.lefarmico.moviesfinder.data.http.response.entity.details.TmdbSpokenLanguage

data class TmdbMovieDetailsResult(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @SerializedName("belongs_to_collection")
    val belongs_to_collection: Any,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: List<TmdbGenre>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdb_id: String,
    @SerializedName("original_language")
    val original_language: String,
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val poster_path: String?,
    @SerializedName("production_companies")
    val production_companies: List<TmdbProductionCompany>,
    @SerializedName("production_countries")
    val production_countries: List<TmdbProductionCountry>,
    @SerializedName("release_date")
    val release_date: String,
    @SerializedName("revenue")
    val revenue: Int,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spoken_languages: List<TmdbSpokenLanguage>,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val vote_average: Float,
    @SerializedName("vote_count")
    val vote_count: Int,
    @SerializedName("watch/providers")
    val providers: TmdbProvidersResult?,
    @SerializedName("credits")
    val credits: TmdbCreditsResult?,
    @SerializedName("recommendations")
    val recommendations: TmdbMovieListResult?,
)
