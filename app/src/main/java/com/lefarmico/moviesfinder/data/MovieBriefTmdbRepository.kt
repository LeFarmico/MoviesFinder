package com.lefarmico.moviesfinder.data

interface MovieBriefTmdbRepository {

    fun getMovieBriefListFromTmdbByCategory(category: String)
}
