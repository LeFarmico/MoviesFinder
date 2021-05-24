package com.lefarmico.remote_module.tmdbEntity.preferences.details

import com.google.gson.annotations.SerializedName

data class TmdbProductionCountry(
    @SerializedName("iso_3166_1")
    val iso_3166_1: String,
    @SerializedName("name")
    val name: String
)
