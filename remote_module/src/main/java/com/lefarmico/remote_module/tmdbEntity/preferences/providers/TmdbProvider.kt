package com.lefarmico.remote_module.tmdbEntity.preferences.providers

import com.google.gson.annotations.SerializedName

data class TmdbProvider(
    @SerializedName("buy")
    val tmdbBuy: List<TmdbBuy>?,
    @SerializedName("flatrate")
    val tmdbFlatrate: List<TmdbFlatrate>?,
    val link: String?,
    @SerializedName("rent")
    val rent: List<TmdbRent>?
)
