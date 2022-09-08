package com.lefarmico.moviesfinder.data.manager.useCase

import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.http.response.entity.State
import kotlinx.coroutines.flow.Flow

fun interface SaveMovieDetailedToDBUseCase : suspend (MovieDetailedData) -> Flow<State<Int>>

fun interface DeleteMovieDetailedFromDBUseCase : suspend (MovieDetailedData) -> Flow<State<Int>>
