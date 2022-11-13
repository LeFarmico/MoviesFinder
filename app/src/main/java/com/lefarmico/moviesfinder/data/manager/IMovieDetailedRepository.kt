package com.lefarmico.moviesfinder.data.manager

import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.http.response.entity.State
import kotlinx.coroutines.flow.Flow

interface IMovieDetailedRepository {
    suspend fun getMovieDetailedData(movieId: Int): Flow<State<MovieDetailedData>>
    suspend fun saveToDBMovieDetailed(movieDetailedData: MovieDetailedData): Flow<State<Int>>
    suspend fun deleteFromDBMovieDetailed(movieDetailedData: MovieDetailedData): Flow<State<Int>>
    suspend fun getFromDBListMovieDetailed(): Flow<State<List<MovieDetailedData>>>
}
