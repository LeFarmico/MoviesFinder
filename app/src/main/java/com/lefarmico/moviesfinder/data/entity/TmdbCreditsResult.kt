package com.lefarmico.moviesfinder.data.entity

import com.lefarmico.moviesfinder.data.entity.credits.Cast
import com.lefarmico.moviesfinder.data.entity.credits.Crew

data class TmdbCreditsResult(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)