package com.lefarmico.moviesfinder.data.http.response.entity

import com.google.gson.annotations.SerializedName
import com.lefarmico.moviesfinder.data.http.response.entity.credits.TmdbCast
import com.lefarmico.moviesfinder.data.http.response.entity.credits.TmdbCrew

data class TmdbCreditsResult(
    @SerializedName("cast")
    val tmdbCast: List<TmdbCast>,
    @SerializedName("crew")
    val tmdbCrew: List<TmdbCrew>,
    val id: Int
)
