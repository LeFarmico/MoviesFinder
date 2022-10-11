package com.lefarmico.moviesfinder.data.http.response.entity.details

import com.google.gson.annotations.SerializedName

data class TmdbSpokenLanguage(
    @SerializedName("english_name")
    val english_name: String,
    @SerializedName("iso_639_1")
    val iso_639_1: String,
    @SerializedName("name")
    val name: String
)
