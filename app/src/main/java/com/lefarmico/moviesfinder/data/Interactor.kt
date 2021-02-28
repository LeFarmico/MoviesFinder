package com.lefarmico.moviesfinder.data

import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.TmdbApi
import com.lefarmico.moviesfinder.data.entity.TmdbMovieDetailsResults
import com.lefarmico.moviesfinder.data.entity.TmdbMovieListResultsResults
import com.lefarmico.moviesfinder.models.CategoryModel
import com.lefarmico.moviesfinder.models.ItemsDataModel
import com.lefarmico.moviesfinder.models.MovieItemModel
import com.lefarmico.moviesfinder.presenters.MovieFragmentPresenter
import com.lefarmico.moviesfinder.private.PrivateData
import com.lefarmico.moviesfinder.utils.Converter
import com.lefarmico.moviesfinder.view.MainView
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

    fun getMovieDetailsFromApi(movieId: Int, mainView: MainView) {
        retrofitService.getMovieDetails(PrivateData.ApiConstants.API_KEY, "ru-RU", movieId)
            .enqueue(object : Callback<TmdbMovieDetailsResults> {
                override fun onResponse(call: Call<TmdbMovieDetailsResults>, response: Response<TmdbMovieDetailsResults>) {
                    mainView.launchItemDetails(
                        MovieItemModel(
                            id = response.body()?.id!!,
                            posterPath = response.body()?.poster_path!!,
                            title = response.body()?.title!!,
                            genreIds = listOf(),
                            rating = response.body()?.vote_average!!,
                            description = response.body()?.poster_path!!,
                            isFavorite = false,
                            genres = listOf()
                        )
                    )
                }
                override fun onFailure(call: Call<TmdbMovieDetailsResults>, t: Throwable) {
                    mainView.showError()
                }
            })
    }
}
