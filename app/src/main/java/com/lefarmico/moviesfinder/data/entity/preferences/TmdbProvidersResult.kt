package com.lefarmico.moviesfinder.data.entity.preferences

import com.lefarmico.moviesfinder.data.entity.preferences.providers.TmdbProvidersResults

data class TmdbProvidersResult(
    val id: Int,
    val results: TmdbProvidersResults
)
