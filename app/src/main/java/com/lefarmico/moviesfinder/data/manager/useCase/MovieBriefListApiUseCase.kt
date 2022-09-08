package com.lefarmico.moviesfinder.data.manager.useCase

import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.http.response.entity.State
import kotlinx.coroutines.flow.Flow

fun interface GetPopularMovieBriefList : suspend (Int) -> Flow<State<MenuItem.Movies>>

fun interface GetUpcomingMovieBriefList : suspend (Int) -> Flow<State<MenuItem.Movies>>

fun interface GetTopRatedMovieBriefList : suspend (Int) -> Flow<State<MenuItem.Movies>>

fun interface GetNowPlayingMovieBriefList : suspend (Int) -> Flow<State<MenuItem.Movies>>
