package com.lefarmico.moviesfinder.data.manager

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.lefarmico.moviesfinder.data.entity.CategoryData
import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.http.request.TmdbApi
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.ui.common.adapter.pagingSource.MovieBriefDataPagingSource
import com.lefarmico.moviesfinder.ui.common.adapter.pagingSource.NETWORK_PAGE_SIZE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieBriefListRepository @Inject constructor(
    private val tmdbApi: TmdbApi
) {

    suspend fun getPopularMovieBriefList() = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.PopularCategory
    )
    suspend fun getNowPlayingMovieBriefList() = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.NowPlayingCategory
    )
    suspend fun getUpcomingMovieBriefList() = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.UpcomingCategory
    )
    suspend fun getTopRatedMovieBriefList() = getMovieBriefListByCategoryAndPage(
        categoryData = CategoryData.TopRatedCategory
    )

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
    ).flow.cachedIn(
        CoroutineScope(Dispatchers.IO)
    ).map {
        State.Success(
            MenuItem.Movies(
                movieCategoryData = categoryData,
                movieBriefDataList = it
            )
        )
    }
}
