package com.lefarmico.moviesfinder.data.manager

interface MovieDataTmdbRepository {

    fun getMovieDataFromTmdb(movieDataId: Int)

    fun findMovieDataFromTmdb(searchRequest: String)
}
