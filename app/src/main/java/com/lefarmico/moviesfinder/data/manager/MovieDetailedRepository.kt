package com.lefarmico.moviesfinder.data.manager

import com.lefarmico.moviesfinder.data.dataBase.dao.ItemDao
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.http.request.TmdbApi
import com.lefarmico.moviesfinder.data.http.response.NetworkResponse
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.private.Private.API_KEY
import com.lefarmico.moviesfinder.utils.mapper.Converter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailedRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val tmdbApi: TmdbApi
) {

    // TODO: encapsulate the request
    suspend fun getMovieDetailedData(movieId: Int): State<MovieDetailedData> {
        val response = tmdbApi.getMovieDetails(
            movieId = movieId,
            apiKey = API_KEY,
            lang = "en-US",
            append = "watch/providers,credits"
        )
        return when (response) {
            is NetworkResponse.Success -> State.Success(
                Converter.convertApiMovieDetailsToDTOItem(response.data)
            )
            is NetworkResponse.Exception -> State.Error(response.throwable)
            is NetworkResponse.Error -> State.Error(
                // TODO add http state error
                RuntimeException()
            )
        }
    }
    suspend fun saveMovieDetailed(movieDetailedData: MovieDetailedData): Flow<State<Int>> = flow {
        emit(State.Loading)
        try {
            val movieId = itemDao.insertMovieDetailed(movieDetailedData)
            emit(State.Success(movieId.toInt()))
        } catch (e: Exception) {
            emit(State.Error(e))
        }
    }

    suspend fun deleteMovieDetailed(movieDetailedData: MovieDetailedData): Flow<State<Int>> = flow {
        emit(State.Loading)
        try {
            val movieId = itemDao.deleteMovie(movieDetailedData)
            emit(State.Success(movieId))
        } catch (e: Exception) {
            emit(State.Error(e))
        }
    }
}
