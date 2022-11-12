package com.lefarmico.moviesfinder.data.manager

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.lefarmico.moviesfinder.data.dataBase.dao.SavedMoviesDao
import com.lefarmico.moviesfinder.data.entity.CategoryData
import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.http.request.TmdbApi
import com.lefarmico.moviesfinder.data.http.response.NetworkResponse
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.data.http.response.entity.TmdbMovieResult
import com.lefarmico.moviesfinder.private.Private.API_KEY
import com.lefarmico.moviesfinder.ui.common.adapter.pagingSource.MovieBriefDataPagingSource
import com.lefarmico.moviesfinder.ui.common.adapter.pagingSource.NETWORK_PAGE_SIZE
import com.lefarmico.moviesfinder.utils.mapper.toBriefViewData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieBriefListRepository @Inject constructor(
    private val tmdbApi: TmdbApi,
    private val savedMoviesDao: SavedMoviesDao
) : IMovieBriefListRepository {

    override suspend fun getPopularMovieBriefList() = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.PopularCategory
    )
    override suspend fun getNowPlayingMovieBriefList() = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.NowPlayingCategory
    )
    override suspend fun getUpcomingMovieBriefList() = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.UpcomingCategory
    )
    override suspend fun getTopRatedMovieBriefList() = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.TopRatedCategory
    )

    override suspend fun getRecommendationsMovieBriefList(movieId: Int): Flow<State<List<MovieBriefData>>> = flow {
        emit(State.Loading)
        when (val response = tmdbApi.getRecommendations(movieId, API_KEY)) {
            is NetworkResponse.Success -> {
                emit(State.Success(response.data.tmdbMovie.toBriefList()))
            }
            is NetworkResponse.Error -> {
                emit(State.Error(RuntimeException("Server side error with code: ${response.code}")))
            }
            is NetworkResponse.Exception -> {
                emit(State.Error(response.throwable))
            }
        }
    }

    private suspend fun getMovieBriefListByCategoryAndPage(
        categoryData: CategoryData,
    ) = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            MovieBriefDataPagingSource(tmdbApi, categoryData)
        }
    ).flow
        .cachedIn(CoroutineScope(Dispatchers.IO))
        .map {
            val movieBriefList = it.map { movieResult -> movieResult.toBriefData() }
            MenuItem.Movies(
                movieCategoryData = categoryData,
                movieBriefDataList = movieBriefList
            )
        }

    private suspend fun TmdbMovieResult.toBriefData(): MovieBriefData {
        val savedMovie = savedMoviesDao.getMovieDetailed(this.id)
        return when (savedMovie != null) {
            true -> this.toBriefViewData(
                isWatchList = savedMovie.isWatchlist,
                yourRate = savedMovie.yourRate
            )
            false -> this.toBriefViewData()
        }
    }

    private suspend fun List<TmdbMovieResult>.toBriefList(): List<MovieBriefData> {
        return this.map { it.toBriefData() }
    }
}
