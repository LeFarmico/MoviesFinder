package com.lefarmico.moviesfinder.data.manager

import com.lefarmico.moviesfinder.data.entity.CategoryData
import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.http.request.TmdbApi
import com.lefarmico.moviesfinder.data.http.response.State
import com.lefarmico.moviesfinder.private.Private
import com.lefarmico.moviesfinder.utils.mapper.Converter
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
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
        try {
            val call =
                tmdbApi.getMovies(
                    category = categoryData.categoryRequestTitle,
                    apiKey = Private.API_KEY,
                    lang = "en-US",
                    page = pageNumber
                )
            val tmdbMovieListResult = call.awaitResponse().body()
            if (tmdbMovieListResult != null) {
                emit(
                    State.Success(
                        MenuItem.Movies(
                            movieCategoryData = categoryData,
                            movieBriefDataList = Converter.convertApiListToDTOList(
                                tmdbMovieListResult.tmdbMovie
                            )
                        )
                    )
                )
            } else {
                emit(State.Error(NullPointerException("Request was executed with failure")))
            }
        } catch (e: Exception) {
            emit(State.Error(e))
        }
    }
}
