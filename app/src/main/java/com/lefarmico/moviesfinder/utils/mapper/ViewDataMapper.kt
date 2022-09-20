package com.lefarmico.moviesfinder.utils.mapper

import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData

fun MovieDetailedData.toBriefData() = MovieBriefData(
    id = this.id,
    movieId = this.movieId,
    posterPath = this.posterPath,
    title = this.title,
    rating = this.rating,
    description = this.description,
    isWatchlist = this.isWatchlist,
    yourRate = this.yourRate,
    releaseDate = this.releaseDate

)
