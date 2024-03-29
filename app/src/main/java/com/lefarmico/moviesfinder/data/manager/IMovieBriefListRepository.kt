package com.lefarmico.moviesfinder.data.manager

import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.http.response.entity.State
import kotlinx.coroutines.flow.Flow

interface IMovieBriefListRepository {
    suspend fun getPopularMovieBriefList(): Flow<MenuItem.Movies>
    suspend fun getNowPlayingMovieBriefList(): Flow<MenuItem.Movies>
    suspend fun getUpcomingMovieBriefList(): Flow<MenuItem.Movies>
    suspend fun getTopRatedMovieBriefList(): Flow<MenuItem.Movies>
    suspend fun getRecommendationsMovieBriefList(movieId: Int): Flow<State<List<MovieBriefData>>>
}
