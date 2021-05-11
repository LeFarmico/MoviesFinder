package com.lefarmico.moviesfinder.data.TmdbEntity.preferences

import com.google.gson.annotations.SerializedName
import com.lefarmico.moviesfinder.data.TmdbEntity.preferences.credits.TmdbCast
import com.lefarmico.moviesfinder.data.TmdbEntity.preferences.credits.TmdbCrew

data class TmdbCreditsResult(
    @SerializedName("cast")
    val tmdbCast: List<TmdbCast>,
    @SerializedName("crew")
    val tmdbCrew: List<TmdbCrew>,
    val id: Int
)
