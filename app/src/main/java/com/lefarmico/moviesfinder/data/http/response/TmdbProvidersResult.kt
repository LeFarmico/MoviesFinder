package com.lefarmico.moviesfinder.data.http.response

import com.lefarmico.moviesfinder.data.http.response.providers.TmdbProvidersResults

data class TmdbProvidersResult(
    val id: Int,
    val results: TmdbProvidersResults
)
