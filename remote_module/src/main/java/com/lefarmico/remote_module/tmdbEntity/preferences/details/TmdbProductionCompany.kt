package com.lefarmico.remote_module.tmdbEntity.preferences.details

import com.google.gson.annotations.SerializedName

data class TmdbProductionCompany(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logo_path: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val origin_country: String
)
