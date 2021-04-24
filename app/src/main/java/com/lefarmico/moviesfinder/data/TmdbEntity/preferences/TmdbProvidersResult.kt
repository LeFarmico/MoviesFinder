package com.lefarmico.moviesfinder.data.TmdbEntity.preferences

import com.lefarmico.moviesfinder.data.TmdbEntity.preferences.providers.TmdbProvidersResults

data class TmdbProvidersResult(
    val id: Int,
    val results: TmdbProvidersResults
)
