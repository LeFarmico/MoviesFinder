package com.lefarmico.moviesfinder.data.TmdbEntity.preferences

import com.lefarmico.moviesfinder.data.TmdbEntity.preferences.credits.TmdbCast
import com.lefarmico.moviesfinder.data.TmdbEntity.preferences.credits.TmdbCrew

data class TmdbCreditsResult(
    val tmdbCast: List<TmdbCast>,
    val tmdbCrew: List<TmdbCrew>,
    val id: Int
)
