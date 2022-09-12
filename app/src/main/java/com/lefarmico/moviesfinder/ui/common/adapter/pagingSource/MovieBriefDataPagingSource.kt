package com.lefarmico.moviesfinder.ui.common.adapter.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lefarmico.moviesfinder.data.entity.CategoryData
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.http.request.TmdbApi
import com.lefarmico.moviesfinder.data.http.response.NetworkResponse
import com.lefarmico.moviesfinder.private.Private.API_KEY
import com.lefarmico.moviesfinder.utils.mapper.Converter

const val NETWORK_PAGE_SIZE = 25
const val TMDB_STARTING_PAGE_INDEX = 1

class MovieBriefDataPagingSource(
    private val tmdbApi: TmdbApi,
    private val categoryData: CategoryData
) : PagingSource<Int, MovieBriefData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieBriefData> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        // TODO change it to sharedPrefs
        val networkResponse = tmdbApi.getMovies(
            category = categoryData.categoryRequestTitle,
            apiKey = API_KEY,
            page = pageIndex,
            lang = "en_US"
        )
        return when (networkResponse) {
            is NetworkResponse.Error -> LoadResult.Error(
                RuntimeException("serverside error. code: ${networkResponse.code}, message: ${networkResponse.message}")
            )
            is NetworkResponse.Exception -> LoadResult.Error(networkResponse.throwable)
            is NetworkResponse.Success -> {
                val nextKey =
                    if (networkResponse.data.tmdbMovie.isEmpty())
                        null
                    else
                        pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                LoadResult.Page(
                    data = Converter.convertApiListToDTOList(networkResponse.data.tmdbMovie),
                    prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                    nextKey = nextKey
                )
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieBriefData>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
}
