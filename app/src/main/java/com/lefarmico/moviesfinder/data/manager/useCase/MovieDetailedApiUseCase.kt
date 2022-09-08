package com.lefarmico.moviesfinder.data.manager.useCase

import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.http.response.entity.State

fun interface GetMovieDetailedApiUseCase : suspend (Int) -> State<MovieDetailedData>
