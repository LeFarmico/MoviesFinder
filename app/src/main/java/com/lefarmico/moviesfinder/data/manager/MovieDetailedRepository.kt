package com.lefarmico.moviesfinder.data.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.lefarmico.moviesfinder.data.dataBase.dao.SavedMoviesDao
import com.lefarmico.moviesfinder.data.dataStore.PreferencesKeys
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.http.request.TmdbApi
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.data.http.response.onError
import com.lefarmico.moviesfinder.data.http.response.onException
import com.lefarmico.moviesfinder.data.http.response.onSuccess
import com.lefarmico.moviesfinder.private.Private.API_KEY
import com.lefarmico.moviesfinder.utils.exception.NetworkResponseException
import com.lefarmico.moviesfinder.utils.mapper.toDetailedViewData
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieDetailedRepository @Inject constructor(
    private val savedMoviesDao: SavedMoviesDao,
    private val tmdbApi: TmdbApi,
    private val dataStore: DataStore<Preferences>
) : IMovieDetailedRepository {

    override suspend fun getMovieDetailedData(movieId: Int): Flow<State<MovieDetailedData>> = flow {
        emit(State.Loading)
        tmdbApi.getMovieDetails(
            movieId = movieId,
            apiKey = API_KEY,
            lang = "en-US",
            append = "watch/providers,credits,recommendations"
        ).apply {
            onSuccess { result ->
                val savedMovie = savedMoviesDao.getMovieDetailed(result.id)
                val recMoviesIdList = result.recommendations?.tmdbMovie?.map { it.id }
                val matchedRecommendations = recMoviesIdList?.let { idList ->
                    savedMoviesDao.getMatchedMovieDetailedByIdList(idList)
                }
                val usersLanguage = dataStore.data.map { pref ->
                    pref[PreferencesKeys.USERS_LANGUAGE] ?: "US"
                }.first()
                emit(
                    State.Success(
                        result.toDetailedViewData(
                            isWatchList = savedMovie?.isWatchlist ?: false,
                            userRate = savedMovie?.yourRate,
                            country = usersLanguage, // TODO delete country
                            matchedRecommendations = matchedRecommendations
                        )
                    )
                )
            }
            onError { code, message ->
                emit(
                    State.Error(
                        NetworkResponseException(code, message)
                    )
                )
            }
            onException { e ->
                emit(
                    State.Error(e)
                )
            }
        }
    }
    override suspend fun saveToDBMovieDetailed(movieDetailedData: MovieDetailedData): Flow<State<Int>> = flow {
        emit(State.Loading)
        try {
            val movieId = savedMoviesDao.insertMovieDetailed(movieDetailedData)
            emit(State.Success(movieId.toInt()))
        } catch (e: Exception) {
            emit(State.Error(e))
        }
    }

    override suspend fun deleteFromDBMovieDetailed(movieDetailedData: MovieDetailedData): Flow<State<Int>> = flow {
        emit(State.Loading)
        try {
            val movieId = savedMoviesDao.deleteMovieDetails(movieDetailedData)
            emit(State.Success(movieId))
        } catch (e: Exception) {
            emit(State.Error(e))
        }
    }

    override suspend fun getFromDBListMovieDetailed(): Flow<State<List<MovieDetailedData>>> = flow {
        emit(State.Loading)
        try {
            val watchListMovies = savedMoviesDao.getWatchListMovieDetailed()
            emit(State.Success(watchListMovies))
        } catch (e: Exception) {
            emit(State.Error(e))
        }
    }
}
