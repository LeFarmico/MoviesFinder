package com.lefarmico.moviesfinder.data

import com.lefarmico.moviesfinder.data.entity.MovieData

interface MovieDataDbRepository {

    fun insertMovieDataToDb(movieData: MovieData)

    fun getMovieDataFromDb(movieDataId: Int)

    fun updateMovieDataToDb(movieData: MovieData)

    fun deleteMovieDataFromDb(movieData: MovieData)
}
