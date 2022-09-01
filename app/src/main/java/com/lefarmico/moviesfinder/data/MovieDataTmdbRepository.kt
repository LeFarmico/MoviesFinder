package com.lefarmico.moviesfinder.data

interface MovieDataTmdbRepository {

    fun getMovieDataFromTmdb(movieDataId: Int)

    fun findMovieDataFromTmdb(searchRequest: String)
}
