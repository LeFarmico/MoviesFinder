package com.lefarmico.moviesfinder.data.manager

import com.lefarmico.moviesfinder.data.entity.MovieDetailedData

interface MovieDataDbRepository {

    fun insertMovieDataToDb(movieDetailedData: MovieDetailedData)

    fun getMovieDataFromDb(movieDataId: Int)

    fun updateMovieDataToDb(movieDetailedData: MovieDetailedData)

    fun deleteMovieDataFromDb(movieDetailedData: MovieDetailedData)
}
