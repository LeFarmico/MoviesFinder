package com.lefarmico.moviesfinder.data

import android.util.Log
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.entity.TmdbApi
import com.lefarmico.moviesfinder.data.entity.TmdbMovieDetailsActorsDirectorsProviders
import com.lefarmico.moviesfinder.data.entity.TmdbMovieListResults
import com.lefarmico.moviesfinder.models.CategoryModel
import com.lefarmico.moviesfinder.models.ItemHeader
import com.lefarmico.moviesfinder.models.ItemsDataModel
import com.lefarmico.moviesfinder.presenters.MainActivityPresenter
import com.lefarmico.moviesfinder.presenters.MovieFragmentPresenter
import com.lefarmico.moviesfinder.private.ApiConstants
import com.lefarmico.moviesfinder.providers.CategoryProvider
import com.lefarmico.moviesfinder.providers.PreferenceProvider
import com.lefarmico.moviesfinder.utils.Converter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferenceProvider: PreferenceProvider) {

    fun getMoviesFromApi(category: CategoryProvider.Category, page: Int, presenter: MovieFragmentPresenter) {
        retrofitService.getMovies(category.categoryTitle, ApiConstants.API_KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbMovieListResults> {
                override fun onResponse(call: Call<TmdbMovieListResults>, response: Response<TmdbMovieListResults>) {
                    Log.d("Interactor", "load category")
                    val list = Converter.convertApiListToDTOList(response.body()?.tmdbMovie)
                    list.forEach {
                        repo.putToDb(item = it)
                    }
                    presenter.loadCategory(
                        CategoryModel(
                            category.getResource(), category,
                            ItemsDataModel(list)
                        )
                    )
                }

                override fun onFailure(call: Call<TmdbMovieListResults>, t: Throwable) {
                    presenter.showError(R.string.error_text)
                }
            })
    }

    fun getMovieDetailsFromApi(itemHeader: ItemHeader, movieId: Int, presenter: MainActivityPresenter) {
        retrofitService.getMovieDetailsWithCreditsAndProviders(movieId, ApiConstants.API_KEY, "ru-RU", "watch/providers,credits")
            .enqueue(object : Callback<TmdbMovieDetailsActorsDirectorsProviders> {
                override fun onResponse(call: Call<TmdbMovieDetailsActorsDirectorsProviders>, response: Response<TmdbMovieDetailsActorsDirectorsProviders>) {
                    presenter.showItemDetails(
                        Converter.convertApiMovieDetailsCreditsProvidersToDTOItem(itemHeader, preferenceProvider.getCurrentCountry(), response.body()!!)
                    )
                }
                override fun onFailure(call: Call<TmdbMovieDetailsActorsDirectorsProviders>, t: Throwable) {
                    presenter.showError(R.string.error_text)
                }
            })
    }
    fun updateMoviesFromApi(category: CategoryProvider.Category, page: Int, adapter: ItemsPlaceholderAdapter) {
        retrofitService.getMovies(category.categoryTitle, ApiConstants.API_KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbMovieListResults> {
                override fun onResponse(call: Call<TmdbMovieListResults>, response: Response<TmdbMovieListResults>) {
                    Log.d("Interactor", "load category")
                    adapter.addNestedItemsData(Converter.convertApiListToDTOList(response.body()?.tmdbMovie))
                }

                override fun onFailure(call: Call<TmdbMovieListResults>, t: Throwable) {
                    // show error
                }
            })
    }
}
