package com.lefarmico.moviesfinder.data.entity.preferences

import com.lefarmico.moviesfinder.data.entity.preferences.providers.ProvidersResults

data class TmdbProvidersResult(
    val id: Int,
    val results: ProvidersResults
)
