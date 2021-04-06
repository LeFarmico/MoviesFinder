package com.lefarmico.moviesfinder.data.entity.preferences

import com.lefarmico.moviesfinder.data.entity.preferences.credits.Cast
import com.lefarmico.moviesfinder.data.entity.preferences.credits.Crew

data class TmdbCreditsResult(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)
