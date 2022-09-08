package com.lefarmico.moviesfinder.data.manager

import com.lefarmico.moviesfinder.data.entity.CategoryData
import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.http.request.TmdbApi
import com.lefarmico.moviesfinder.data.http.response.NetworkResponse
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.private.Private
import com.lefarmico.moviesfinder.utils.mapper.Converter
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieBriefListRepository @Inject constructor(
    private val tmdbApi: TmdbApi
) {

    suspend fun getPopularMovieBriefList(pageNumber: Int) = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.PopularCategory,
        pageNumber = pageNumber
    )
    suspend fun getNowPlayingMovieBriefList(pageNumber: Int) = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.NowPlayingCategory,
        pageNumber = pageNumber
    )
    suspend fun getUpcomingMovieBriefList(pageNumber: Int) = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.UpcomingCategory,
        pageNumber = pageNumber
    )
    suspend fun getTopRatedMovieBriefList(pageNumber: Int) = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.TopRatedCategory,
        pageNumber = pageNumber
    )

    private suspend fun getMovieBriefListByCategoryAndPage(
        categoryData: CategoryData,
        pageNumber: Int
    ) = flow {
        emit(State.Loading)
        val response =
            tmdbApi.getMovies(
                category = categoryData.categoryRequestTitle,
                apiKey = Private.API_KEY,
                lang = "en-US",
                page = pageNumber
            )
        when (response) {
            is NetworkResponse.Success -> {
                emit(
                    State.Success(
                        MenuItem.Movies(
                            movieCategoryData = categoryData,
                            movieBriefDataList = Converter.convertApiListToDTOList(
                                response.data.tmdbMovie
                            )
                        )
                    )
                )
            }
            is NetworkResponse.Error -> {
                emit(State.Error(NullPointerException("Response contains an error")))
            }
            is NetworkResponse.Exception -> {
                emit(State.Error(NullPointerException("Request was executed with failure")))
            }
        }
    }
}
