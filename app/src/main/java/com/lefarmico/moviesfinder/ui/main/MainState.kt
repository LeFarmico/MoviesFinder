package com.lefarmico.moviesfinder.ui.main

import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.ui.main.adapter.model.MovieDetailsModel

data class MainState(
    val isLoading: Boolean = false,
    val shownMovie: ShownMovie? = null,
    val toast: String? = null
)

data class ShownMovie(
    val movieData: MovieDetailedData,
    val movieDetailsModelList: List<MovieDetailsModel>
)
