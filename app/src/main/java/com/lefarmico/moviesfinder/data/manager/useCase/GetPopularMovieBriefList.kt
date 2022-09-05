package com.lefarmico.moviesfinder.data.manager.useCase

import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.http.response.State
import kotlinx.coroutines.flow.Flow

fun interface GetPopularMovieBriefList {
    suspend fun byPage(pageNumber: Int): Flow<State<MenuItem.Movies>>
}

fun interface GetUpcomingMovieBriefList {
    suspend fun byPage(pageNumber: Int): Flow<State<MenuItem.Movies>>
}

fun interface GetTopRatedMovieBriefList {
    suspend fun byPage(pageNumber: Int): Flow<State<MenuItem.Movies>>
}

fun interface GetNowPlayingMovieBriefList {
    suspend fun byPage(pageNumber: Int): Flow<State<MenuItem.Movies>>
}
