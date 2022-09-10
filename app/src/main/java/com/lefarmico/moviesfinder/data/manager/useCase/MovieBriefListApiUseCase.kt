package com.lefarmico.moviesfinder.data.manager.useCase

import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.http.response.entity.State
import kotlinx.coroutines.flow.Flow

fun interface GetPopularMovieBriefList : suspend () -> Flow<State<MenuItem.Movies>>

fun interface GetUpcomingMovieBriefList : suspend () -> Flow<State<MenuItem.Movies>>

fun interface GetTopRatedMovieBriefList : suspend () -> Flow<State<MenuItem.Movies>>

fun interface GetNowPlayingMovieBriefList : suspend () -> Flow<State<MenuItem.Movies>>
