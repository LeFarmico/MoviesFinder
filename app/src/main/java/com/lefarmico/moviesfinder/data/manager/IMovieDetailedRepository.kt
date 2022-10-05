package com.lefarmico.moviesfinder.data.manager

import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.http.response.entity.State
import kotlinx.coroutines.flow.Flow

interface IMovieDetailedRepository {
    suspend fun getMovieDetailedData(movieId: Int): State<MovieDetailedData>
    suspend fun saveMovieDetailed(movieDetailedData: MovieDetailedData): Flow<State<Int>>
    suspend fun deleteMovieDetailed(movieDetailedData: MovieDetailedData): Flow<State<Int>>
    suspend fun getWatchListMovieDetailed(): Flow<State<List<MovieDetailedData>>>
}
