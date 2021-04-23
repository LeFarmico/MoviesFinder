package com.lefarmico.moviesfinder.data

import android.util.Log
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.appEntity.Category
import com.lefarmico.moviesfinder.data.appEntity.CategoryDb
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.data.entity.TmdbApi
import com.lefarmico.moviesfinder.data.entity.preferences.TmdbMovieDetailsWithCreditsAndProvidersResult
import com.lefarmico.moviesfinder.data.entity.preferences.TmdbMovieListResult
import com.lefarmico.moviesfinder.private.ApiConstants
import com.lefarmico.moviesfinder.providers.CategoryProvider
import com.lefarmico.moviesfinder.providers.PreferenceProvider
import com.lefarmico.moviesfinder.utils.Converter
import com.lefarmico.moviesfinder.viewModels.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferenceProvider: PreferenceProvider) {

    val scope = CoroutineScope(Dispatchers.IO)
    val isLoadingProgressBarShown = Channel<Boolean>(Channel.CONFLATED)

    fun getMovieCategoryFromApi(categoryType: CategoryProvider.Category, page: Int) {

        scope.launch {
            isLoadingProgressBarShown.send(true)
        }

        retrofitService.getMovies(categoryType.categoryTitle, ApiConstants.API_KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbMovieListResult> {

                override fun onResponse(call: Call<TmdbMovieListResult>, response: Response<TmdbMovieListResult>) {
                    Log.d("Interactor", "load category")

                    val list = Converter.convertApiListToDTOList(response.body()?.tmdbMovie)
                    val category = CategoryDb(categoryType, categoryType.getResource())

                    scope.launch {
                        repo.putCategoryDd(category)
                        repo.putItemHeadersToDb(list)
                        repo.putMoviesByCategoryDB(category, list)

                        isLoadingProgressBarShown.send(element = false)
                    }
                }
                override fun onFailure(call: Call<TmdbMovieListResult>, t: Throwable) {
                    scope.launch {
                        isLoadingProgressBarShown.send(false)
                    }
                }
            })
    }

    fun getCategoriesFromDB(categoryType: CategoryProvider.Category): Category {
        return repo.getCategoryFromDB(categoryType)
    }

    suspend fun getMovieDetailsFromApi(itemHeader: ItemHeader, movieId: Int, viewModel: MainActivityViewModel) {
        retrofitService.getMovieDetailsWithCreditsAndProviders(movieId, ApiConstants.API_KEY, "ru-RU", "watch/providers,credits")
            .enqueue(object : Callback<TmdbMovieDetailsWithCreditsAndProvidersResult> {
                override fun onResponse(call: Call<TmdbMovieDetailsWithCreditsAndProvidersResult>, response: Response<TmdbMovieDetailsWithCreditsAndProvidersResult>) {
                    val movie = Converter.convertApiMovieDetailsCreditsProvidersToDTOItem(
                        itemHeader,
                        preferenceProvider.getCurrentCountry(),
                        response.body()!!
                    )
                    viewModel.showItemDetails(movie)

                    val scope = CoroutineScope(Dispatchers.IO)
                    scope.launch {
                        repo.putMovieToDb(movie)
                    }
                }
                override fun onFailure(call: Call<TmdbMovieDetailsWithCreditsAndProvidersResult>, t: Throwable) {
                    viewModel.onFailureItemDetails(R.string.error_text)
                }
            })
    }
    fun updateMoviesFromApi(category: CategoryProvider.Category, page: Int, adapter: ItemsPlaceholderAdapter) {
        retrofitService.getMovies(category.categoryTitle, ApiConstants.API_KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbMovieListResult> {
                override fun onResponse(call: Call<TmdbMovieListResult>, response: Response<TmdbMovieListResult>) {
                    Log.d("Interactor", "load category")
                    adapter.addNestedItemsData(Converter.convertApiListToDTOList(response.body()?.tmdbMovie).toMutableList())
                }

                override fun onFailure(call: Call<TmdbMovieListResult>, t: Throwable) {
                    // show error
                }
            })
    }
}
