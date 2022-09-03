package com.lefarmico.moviesfinder.data.manager

interface MovieBriefTmdbRepository {

    fun getMovieBriefListFromTmdbByCategory(category: String)
}
