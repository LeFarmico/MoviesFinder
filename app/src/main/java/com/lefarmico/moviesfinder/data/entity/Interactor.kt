package com.lefarmico.moviesfinder.data.entity

import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.models.CategoryModel
import com.lefarmico.moviesfinder.models.ItemsDataModel
import com.lefarmico.moviesfinder.presenters.MoviePresenter
import com.lefarmico.moviesfinder.private.PrivateData
import com.lefarmico.moviesfinder.utils.Converter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi) {

    fun getPopularMovieFromApi(page: Int, presenter: MoviePresenter) {
        retrofitService.getPopularMovies(PrivateData.ApiConstants.API_KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbResults> {
                override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                    presenter.loadCategory(
                        CategoryModel(
                            R.string.popular, 1,
                            ItemsDataModel(Converter.convertApiListToDTOList(response.body()?.tmdbMovie))
                        )
                    )
                }

                override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                    presenter.showError(R.string.error_text)
                }
            })
    }

    fun getLatestMovieFromApi(page: Int, presenter: MoviePresenter) {
        retrofitService.getUpcomingMovies(PrivateData.ApiConstants.API_KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbResults> {
                override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                    println("!!! ${response.body()}")
                    presenter.loadCategory(
                        CategoryModel(
                            R.string.upcoming, 2,
                            ItemsDataModel(Converter.convertApiListToDTOList(response.body()?.tmdbMovie))
                        )
                    )
                }
                override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                    presenter.showError(R.string.error_text)
                }
            })
    }
}
