package com.lefarmico.moviesfinder.data.entity.preferences

import com.lefarmico.moviesfinder.data.entity.preferences.credits.TmdbCast
import com.lefarmico.moviesfinder.data.entity.preferences.credits.TmdbCrew

data class TmdbCreditsResult(
    val tmdbCast: List<TmdbCast>,
    val tmdbCrew: List<TmdbCrew>,
    val id: Int
)
