package com.lefarmico.moviesfinder.data.http.response.entity

import com.lefarmico.moviesfinder.data.http.response.entity.providers.TmdbProvidersResults

data class TmdbProvidersResult(
    val id: Int,
    val results: TmdbProvidersResults
)
