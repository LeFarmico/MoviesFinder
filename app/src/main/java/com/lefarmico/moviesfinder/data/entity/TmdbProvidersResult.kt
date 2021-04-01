package com.lefarmico.moviesfinder.data.entity

import com.lefarmico.moviesfinder.data.entity.providers.ProvidersResults

data class TmdbProvidersResult(
    val id: Int,
    val results: ProvidersResults
)
