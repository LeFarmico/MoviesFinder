package com.lefarmico.moviesfinder.data

import android.util.Log
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.TmdbEntity.TmdbApi
import com.lefarmico.moviesfinder.data.TmdbEntity.preferences.TmdbMovieDetailsResult
import com.lefarmico.moviesfinder.data.TmdbEntity.preferences.TmdbMovieListResult
import com.lefarmico.moviesfinder.data.appEntity.*
import com.lefarmico.moviesfinder.private.ApiConstants
import com.lefarmico.moviesfinder.providers.CategoryProvider
import com.lefarmico.moviesfinder.providers.PreferenceProvider
import com.lefarmico.moviesfinder.utils.Converter
import com.lefarmico.moviesfinder.viewModels.MainActivityViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferenceProvider: PreferenceProvider) {

    val scope = CoroutineScope(Dispatchers.IO)
    val progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()
    val bottomSheetProgressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()
    val isFragmentLoadingProgressBarShown = Channel<Boolean>(Channel.CONFLATED)
    val isBottomSheetLoadingProgressBarShown = Channel<Boolean>(Channel.CONFLATED)

    fun getMovieCategoryFromApi(categoryType: CategoryProvider.Category, page: Int) {

        progressBarState.onNext(true)

        retrofitService.getMovies(categoryType.categoryTitle, ApiConstants.API_KEY, "en-US", page)
            .enqueue(object : Callback<TmdbMovieListResult> {

                override fun onResponse(
                    call: Call<TmdbMovieListResult>,
                    response: Response<TmdbMovieListResult>
                ) {
                    Log.d("Interactor", "load category")

                    val itemList = Converter.convertApiListToDTOList(response.body()?.tmdbMovie)
                    val category = CategoryDb(categoryType, categoryType.getResource())

                    Completable.fromSingle<Category> {
                        repo.putCategoryDd(category)
                        repo.putItemHeadersToDb(itemList)
                        repo.putMoviesByCategoryDB(category, itemList)
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                    progressBarState.onNext(false)
                }
                override fun onFailure(call: Call<TmdbMovieListResult>, t: Throwable) {
                    progressBarState.onNext(false)
                }
            })
    }

    fun getMovieDetailsFromApi(itemHeader: ItemHeader, movieId: Int, viewModel: MainActivityViewModel) {

        bottomSheetProgressBarState.onNext(true)

        retrofitService.getMovieDetails(movieId, ApiConstants.API_KEY, "en-US", "watch/providers,credits")
            .enqueue(object : Callback<TmdbMovieDetailsResult> {

                override fun onResponse(
                    call: Call<TmdbMovieDetailsResult>,
                    response: Response<TmdbMovieDetailsResult>
                ) {
                    val movie = Converter.convertApiMovieDetailsToDTOItem(
                        itemHeader,
                        preferenceProvider.getCurrentCountry(),
                        response.body()!!
                    )

                    Completable.fromSingle<Movie> {
                        bottomSheetProgressBarState.onNext(false)
                        viewModel.showItemDetails(movie)
                        repo.putMovieToDb(movie)
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                }

                override fun onFailure(call: Call<TmdbMovieDetailsResult>, t: Throwable) {
                    bottomSheetProgressBarState.onNext(false)
                }
            })
    }
    fun updateMoviesFromApi(category: CategoryProvider.Category, page: Int, adapter: ItemsPlaceholderAdapter) {
        retrofitService.getMovies(category.categoryTitle, ApiConstants.API_KEY, "en-US", page)
            .enqueue(object : Callback<TmdbMovieListResult> {

                override fun onResponse(call: Call<TmdbMovieListResult>, response: Response<TmdbMovieListResult>) {
                    Log.d("Interactor", "load category")

                    val itemList = Converter.convertApiListToDTOList(response.body()?.tmdbMovie).toMutableList()

                    scope.launch {
                        repo.putItemHeadersToDb(itemList)
                        withContext(Dispatchers.Main) {
                            adapter.addItems(itemList)
                        }
                    }
                }

                override fun onFailure(call: Call<TmdbMovieListResult>, t: Throwable) {
                    // show error
                }
            })
    }

    fun updateItemHeader(itemHeaderImpl: ItemHeaderImpl) {
        scope.launch {
            repo.updateItemHeader(itemHeaderImpl)
        }
    }

    fun getCategoriesFromDB(categoryType: CategoryProvider.Category): Category {
        return repo.getCategoryFromDB(categoryType)
    }
}
