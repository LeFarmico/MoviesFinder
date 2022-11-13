package com.lefarmico.moviesfinder.ui.movie

import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.entity.MovieDetailsModel

data class MovieFragmentState(
    val isLoading: Boolean = false,
    val movieData: MovieDetailedData? = null,
    val movieDetailsModelList: List<MovieDetailsModel> = listOf()
)
