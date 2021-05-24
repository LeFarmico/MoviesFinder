package com.lefarmico.remote_module.tmdbEntity.preferences

import com.google.gson.annotations.SerializedName
import com.lefarmico.remote_module.tmdbEntity.preferences.credits.TmdbCast
import com.lefarmico.remote_module.tmdbEntity.preferences.credits.TmdbCrew

data class TmdbCreditsResult(
    @SerializedName("cast")
    val tmdbCast: List<TmdbCast>,
    @SerializedName("crew")
    val tmdbCrew: List<TmdbCrew>,
    val id: Int
)
