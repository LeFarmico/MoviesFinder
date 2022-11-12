package com.lefarmico.moviesfinder.data.manager.useCase

import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.http.response.entity.State
import kotlinx.coroutines.flow.Flow

fun interface GetPopularMovieBriefListUseCase : suspend () -> Flow<MenuItem.Movies>

fun interface GetUpcomingMovieBriefListUseCase : suspend () -> Flow<MenuItem.Movies>

fun interface GetTopRatedMovieBriefListUseCase : suspend () -> Flow<MenuItem.Movies>

fun interface GetNowPlayingMovieBriefListUseCase : suspend () -> Flow<MenuItem.Movies>

fun interface GetRecommendationsMovieBriefListUseCase : suspend (Int) -> Flow<State<List<MovieBriefData>>>
