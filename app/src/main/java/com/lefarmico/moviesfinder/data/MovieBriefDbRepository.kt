package com.lefarmico.moviesfinder.data

import com.lefarmico.moviesfinder.data.entity.MovieBriefData

interface MovieBriefDbRepository {

    fun insertMovieBriefToDb(movieBriefData: MovieBriefData)

    fun getMovieBriefFromDb(movieBriefId: Int)

    fun updateMovieBriefFromDb(movieBriefData: MovieBriefData)

    fun deleteMovieBriefFromDb(movieBriefData: MovieBriefData)
}
