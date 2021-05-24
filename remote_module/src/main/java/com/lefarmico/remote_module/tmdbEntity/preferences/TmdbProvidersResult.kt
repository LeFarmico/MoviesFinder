package com.lefarmico.remote_module.tmdbEntity.preferences

import com.lefarmico.remote_module.tmdbEntity.preferences.providers.TmdbProvidersResults

data class TmdbProvidersResult(
    val id: Int,
    val results: TmdbProvidersResults
)
