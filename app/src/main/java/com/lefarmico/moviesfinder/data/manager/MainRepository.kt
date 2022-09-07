package com.lefarmico.moviesfinder.data.manager

import com.lefarmico.moviesfinder.data.dataBase.dao.ItemDao
import com.lefarmico.moviesfinder.data.entity.*
import com.lefarmico.moviesfinder.data.http.request.TmdbApi
import com.lefarmico.moviesfinder.data.http.response.State
import com.lefarmico.moviesfinder.private.Private.API_KEY
import com.lefarmico.moviesfinder.utils.mapper.Converter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val tmdbApi: TmdbApi
) {

    suspend fun getMovieDetailedData(movieId: Int): State<MovieDetailedData> {
        val response = tmdbApi.getMovieDetails(
            movieId = movieId,
            apiKey = API_KEY,
            lang = "en-US",
            append = "watch/providers,credits"
        ).awaitResponse()
        return if (response.isSuccessful) {
            State.Success(
                Converter.convertApiMovieDetailsToDTOItem(
                    response.body()!!
                )
            )
        } else {
            State.Error(
                RuntimeException()
            )
        }
    }
    fun saveMovieDetailed(movieDetailedData: MovieDetailedData): Flow<State<Int>> = flow {
        emit(State.Loading)
        try {
            val movieId = itemDao.insertMovieDetailed(movieDetailedData)
            emit(State.Success(movieId.toInt()))
        } catch (e: Exception) {
            emit(State.Error(e))
        }
    }

    fun deleteMovieDetailed(movieDetailedData: MovieDetailedData): Flow<State<Int>> = flow {
        emit(State.Loading)
        try {
            val movieId = itemDao.deleteMovie(movieDetailedData)
            emit(State.Success(movieId))
        } catch (e: Exception) {
            emit(State.Error(e))
        }
    }
}
