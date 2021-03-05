package com.lefarmico.moviesfinder.data

import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.TmdbApi
import com.lefarmico.moviesfinder.data.entity.TmdbMovieDetailsActorsDirectorsProviders
import com.lefarmico.moviesfinder.data.entity.TmdbMovieDetailsResults
import com.lefarmico.moviesfinder.data.entity.TmdbMovieListResultsResults
import com.lefarmico.moviesfinder.models.CategoryModel
import com.lefarmico.moviesfinder.models.ItemHeader
import com.lefarmico.moviesfinder.models.ItemsDataModel
import com.lefarmico.moviesfinder.presenters.MainActivityPresenter
import com.lefarmico.moviesfinder.presenters.MovieFragmentPresenter
import com.lefarmico.moviesfinder.private.PrivateData
import com.lefarmico.moviesfinder.utils.Converter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi) {

    fun getPopularMovieFromApi(page: Int, presenter: MovieFragmentPresenter) {
        retrofitService.getPopularMovies(PrivateData.ApiConstants.API_KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbMovieListResultsResults> {
                override fun onResponse(call: Call<TmdbMovieListResultsResults>, response: Response<TmdbMovieListResultsResults>) {
                    presenter.loadCategory(
                        CategoryModel(
                            R.string.popular, 1,
                            ItemsDataModel(Converter.convertApiListToDTOList(response.body()?.tmdbMovie))
                        )
                    )
                }

                override fun onFailure(call: Call<TmdbMovieListResultsResults>, t: Throwable) {
                    presenter.showError(R.string.error_text)
                }
            })
    }

    fun getUpcomingMovieFromApi(page: Int, presenter: MovieFragmentPresenter) {
        retrofitService.getUpcomingMovies(PrivateData.ApiConstants.API_KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbMovieListResultsResults> {
                override fun onResponse(call: Call<TmdbMovieListResultsResults>, response: Response<TmdbMovieListResultsResults>) {
                    presenter.loadCategory(
                        CategoryModel(
                            R.string.upcoming, 2,
                            ItemsDataModel(Converter.convertApiListToDTOList(response.body()?.tmdbMovie))
                        )
                    )
                }
                override fun onFailure(call: Call<TmdbMovieListResultsResults>, t: Throwable) {
                    presenter.showError(R.string.error_text)
                }
            })
    }

    fun getTopRatedMovieFromApi(page: Int, presenter: MovieFragmentPresenter) {
        retrofitService.getTopRatedMovies(PrivateData.ApiConstants.API_KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbMovieListResultsResults> {
                override fun onResponse(call: Call<TmdbMovieListResultsResults>, response: Response<TmdbMovieListResultsResults>) {
                    presenter.loadCategory(
                        CategoryModel(
                            R.string.top_rated, 2,
                            ItemsDataModel(Converter.convertApiListToDTOList(response.body()?.tmdbMovie))
                        )
                    )
                }
                override fun onFailure(call: Call<TmdbMovieListResultsResults>, t: Throwable) {
                    presenter.showError(R.string.error_text)
                }
            })
    }

    fun getNowPlayingMovieFromApi(page: Int, presenter: MovieFragmentPresenter) {
        retrofitService.getNowPlayingMovies(PrivateData.ApiConstants.API_KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbMovieListResultsResults> {
                override fun onResponse(call: Call<TmdbMovieListResultsResults>, response: Response<TmdbMovieListResultsResults>) {
                    presenter.loadCategory(
                        CategoryModel(
                            R.string.now_playing, 2,
                            ItemsDataModel(Converter.convertApiListToDTOList(response.body()?.tmdbMovie))
                        )
                    )
                }
                override fun onFailure(call: Call<TmdbMovieListResultsResults>, t: Throwable) {
                    presenter.showError(R.string.error_text)
                }
            })
    }

    fun getMovieDetailsFromApi(itemHeader: ItemHeader, movieId: Int, presenter: MainActivityPresenter) {
        retrofitService.getMovieDetails(movieId, PrivateData.ApiConstants.API_KEY, "ru-RU")
            .enqueue(object : Callback<TmdbMovieDetailsResults> {
                override fun onResponse(call: Call<TmdbMovieDetailsResults>, response: Response<TmdbMovieDetailsResults>) {
                    presenter.showItemDetails(
                        Converter.convertApiMovieDetailsToDTOItem(itemHeader, response.body()!!)
                    )
                }
                override fun onFailure(call: Call<TmdbMovieDetailsResults>, t: Throwable) {
                    presenter.showError(R.string.error_text)
                }
            })
    }
    fun getMovieDetailsCreditsProvidersFromApi(itemHeader: ItemHeader, movieId: Int, presenter: MainActivityPresenter) {
        retrofitService.getMovieDetailsWithCreditsAndProviders(movieId, PrivateData.ApiConstants.API_KEY, "ru-RU", "watch/providers,credits")
            .enqueue(object : Callback<TmdbMovieDetailsActorsDirectorsProviders> {
                override fun onResponse(call: Call<TmdbMovieDetailsActorsDirectorsProviders>, response: Response<TmdbMovieDetailsActorsDirectorsProviders>) {
                    presenter.showItemDetails(
                        Converter.convertApiMovieDetailsCreditsProvidersToDTOItem(itemHeader, response.body()!!)
                    )
                }
                override fun onFailure(call: Call<TmdbMovieDetailsActorsDirectorsProviders>, t: Throwable) {
                    presenter.showError(R.string.error_text)
                }
            })
    }
}
