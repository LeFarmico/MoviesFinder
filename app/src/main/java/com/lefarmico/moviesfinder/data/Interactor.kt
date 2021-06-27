package com.lefarmico.moviesfinder.data

import android.util.Log
import com.lefarmico.moviesfinder.data.appEntity.CategoryDb
import com.lefarmico.moviesfinder.data.appEntity.Header
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.data.appEntity.Movie
import com.lefarmico.moviesfinder.private.ApiConstants.API_KEY
import com.lefarmico.moviesfinder.providers.CategoryProvider
import com.lefarmico.moviesfinder.providers.PreferenceProvider
import com.lefarmico.moviesfinder.utils.Converter
import com.lefarmico.remote_module.tmdbEntity.TmdbApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class Interactor(
    private val repo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferenceProvider: PreferenceProvider
) {

    val loadingState: BehaviorSubject<Boolean> = BehaviorSubject.create()
    val movieDetailsBehaviourSubject: BehaviorSubject<Movie> = BehaviorSubject.create()

    fun putToDbMovieCategoryFromApi(categoryType: CategoryProvider.Category, page: Int) {
        loadingState.onNext(true)
        retrofitService.getMovies(categoryType.categoryTitle, API_KEY, "en-US", page)
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiListToDTOList(it.tmdbMovie)
            }
            .subscribeBy(
                onError = {
                    loadingState.onNext(false)
                },
                onNext = { itemList ->
                    loadingState.onNext(false)
                    val category = CategoryDb(categoryType, categoryType.getResource())
                    repo.putCategoryToDb(category)
                    repo.putItemHeadersToDb(itemList)
                    repo.putMoviesByCategoryToDb(category, itemList)
                }
            )
    }

    fun putMovieFromApiToBehaviour(itemHeader: ItemHeader) {
        retrofitService.getMovieDetails(itemHeader.itemId, API_KEY, "en-US", "watch/providers,credits")
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiMovieDetailsToDTOItem(
                    itemHeader,
                    preferenceProvider.getCurrentCountry(),
                    it
                )
            }.subscribeBy(
                onNext = { movie ->
                    movieDetailsBehaviourSubject.onNext(movie)
                },
                onError = {
                    return@subscribeBy
                }
            )
    }
    fun updateMoviesFromApi(category: CategoryProvider.Category, page: Int, paginate: (MutableList<Header>) -> Unit) {
        retrofitService.getMovies(category.categoryTitle, API_KEY, "en-US", page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Converter.convertApiListToDTOList(it.tmdbMovie).toMutableList()
            }.subscribeBy(
                onError = { Log.e("Interactor", it.message!!) },
                onNext = { itemList ->
                    paginate(itemList)
                }
            )
    }

    fun putMovieDetailsToDb(movie: Movie) {
        repo.putMovieToDb(movie)
    }

    fun deleteMovieDetailsFromDb(movie: Movie) {
        repo.deleteMovieFromDB(movie)
    }

    fun updateItemHeaderInDb(itemHeader: ItemHeader) {
        repo.updateItemHeader(itemHeader as Header)
    }

    fun getCategoriesFromDb(categoryType: CategoryProvider.Category, action: (List<Header>) -> Unit) {
        repo.getCategoryFromDB(categoryType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { action(it) },
                { Log.e("Interactor", it.message!!) }
            )
    }

    fun getSearchResultFromApi(searchQuery: String): Observable<List<Header>> =
        retrofitService.getMovieFromSearch(API_KEY, "en-US", searchQuery, 1)
            .subscribeOn(Schedulers.io())
            .map { result ->
                Converter.convertApiListToDTOList(result.tmdbMovie)
            }

    fun getLastedSearchRequestsFromDb(): Single<List<String>> = repo.getSearchRequests()

    fun putSearchRequestToDb(requestText: String) {
        Single.create<String> {
            it.onSuccess(requestText)
        }
            .subscribeOn(Schedulers.io())
            .subscribe { request ->
                repo.putSearchRequestToDB(request)
            }
    }

    fun getFavoriteMoviesFromDb(): Single<List<Header>> = repo.getFavoriteMovies()
}
