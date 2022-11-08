package com.lefarmico.moviesfinder.ui.movie

import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.entity.MovieDetailsAdapterModel

data class MovieFragmentState(
    val isLoading: Boolean = false,
    val movieData: MovieDetailedData? = null,
    val movieDetailsAdapterModelList: List<MovieDetailsAdapterModel> = listOf()
)
