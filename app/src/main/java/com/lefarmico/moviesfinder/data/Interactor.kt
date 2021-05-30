package com.lefarmico.moviesfinder.data

import android.util.Log
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.appEntity.CategoryDb
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.data.appEntity.ItemHeaderImpl
import com.lefarmico.moviesfinder.data.appEntity.Movie
import com.lefarmico.moviesfinder.private.ApiConstants
import com.lefarmico.moviesfinder.private.ApiConstants.API_KEY
import com.lefarmico.moviesfinder.providers.CategoryProvider
import com.lefarmico.moviesfinder.providers.PreferenceProvider
import com.lefarmico.moviesfinder.utils.Converter
import com.lefarmico.moviesfinder.viewModels.MainActivityViewModel
import com.lefarmico.remote_module.tmdbEntity.TmdbApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferenceProvider: PreferenceProvider) {

    val progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()
    val bottomSheetProgressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun putToDbMovieCategoryFromApi(categoryType: CategoryProvider.Category, page: Int) {
        progressBarState.onNext(true)
        retrofitService.getMovies(categoryType.categoryTitle, API_KEY, "en-US", page)
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiListToDTOList(it.tmdbMovie)
            }
            .subscribeBy(
                onError = {
                    progressBarState.onNext(false)
                },
                onNext = { itemList ->
                    progressBarState.onNext(false)
                    val category = CategoryDb(categoryType, categoryType.getResource())
                    repo.putCategoryDd(category)
                    repo.putItemHeadersToDb(itemList)
                    repo.putMoviesByCategoryDB(category, itemList)
                }
            )
    }

    fun getMovieDetailsFromApi(itemHeader: ItemHeader, movieId: Int, viewModel: MainActivityViewModel) {
        bottomSheetProgressBarState.onNext(true)
        retrofitService.getMovieDetails(movieId, ApiConstants.API_KEY, "en-US", "watch/providers,credits")
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiMovieDetailsToDTOItem(
                    itemHeader,
                    preferenceProvider.getCurrentCountry(),
                    it
                )
            }.subscribeBy(
                onNext = { movie ->
                    bottomSheetProgressBarState.onNext(false)
                    viewModel.showItemDetails(movie)
                }
            )
    }
    fun updateMoviesFromApi(category: CategoryProvider.Category, page: Int, adapter: ItemsPlaceholderAdapter) {
        retrofitService.getMovies(category.categoryTitle, ApiConstants.API_KEY, "en-US", page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Converter.convertApiListToDTOList(it.tmdbMovie).toMutableList()
            }.subscribeBy(
                onError = { Log.e("Interactor", it.message!!) },
                onNext = { itemList ->
                    adapter.addItems(itemList)
                }
            )
    }

    fun putMovieDetails(movie: Movie) {
        repo.putMovieToDb(movie)
    }

    fun deleteMovieDetails(movie: Movie) {
        repo.deleteMovieFromDB(movie)
    }

    fun updateItemHeader(itemHeaderImpl: ItemHeaderImpl) {
        repo.updateItemHeader(itemHeaderImpl)
    }

    fun getCategoriesFromDB(categoryType: CategoryProvider.Category): Single<List<ItemHeaderImpl>> {
        return repo.getCategoryFromDB(categoryType)
    }

    fun getSearchResultFromApi(searchQuery: String): Observable<List<ItemHeaderImpl>> =
        retrofitService.getMovieFromSearch(API_KEY, "en-US", searchQuery, 1)
            .subscribeOn(Schedulers.io())
            .map { result ->
                Converter.convertApiListToDTOList(result.tmdbMovie)
            }

    fun getLastedSearchQueries() {
    }
}
