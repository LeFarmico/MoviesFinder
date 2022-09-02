package com.lefarmico.moviesfinder.data.manager

import android.util.Log
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.entity.MovieData
import com.lefarmico.moviesfinder.private.ApiConstants.API_KEY
import com.lefarmico.moviesfinder.ui.common.provider.CategoryProvider
import com.lefarmico.moviesfinder.ui.common.provider.PreferenceProvider
import com.lefarmico.moviesfinder.utils.mapper.Converter
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
    val movieDataDetailsBehaviourSubject: BehaviorSubject<MovieData> = BehaviorSubject.create()

    fun getMovieBriefListFromApi(categoryType: CategoryProvider.Category, page: Int) {
        loadingState.onNext(true)
        retrofitService.getMovies(categoryType.categoryTitle, API_KEY, "en-US", page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                // TODO: normal mapper
                Converter.convertApiListToDTOList(it.tmdbMovie)
            }
            .doOnNext { movieBriefList ->
                loadingState.onNext(false)
            }
            .subscribe()
    }

    fun putMovieFromApiToBehaviour(movieBriefData: MovieBriefData) {
        retrofitService.getMovieDetails(movieBriefData.itemId, API_KEY, "en-US", "watch/providers,credits")
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiMovieDetailsToDTOItem(
                    movieBriefData,
                    preferenceProvider.getCurrentCountry(),
                    it
                )
            }.subscribeBy(
                onNext = { movie ->
                    movieDataDetailsBehaviourSubject.onNext(movie)
                },
                onError = {
                    return@subscribeBy
                }
            )
    }
    fun updateMoviesFromApi(category: CategoryProvider.Category, page: Int, paginate: (MutableList<MovieBriefData>) -> Unit) {
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

    fun putMovieDetailsToDb(movieData: MovieData) {
        repo.putMovieToDb(movieData)
    }

    fun deleteMovieDetailsFromDb(movieData: MovieData) {
        repo.deleteMovieFromDB(movieData)
    }

    fun updateItemHeaderInDb(movieBriefData: MovieBriefData) {
        repo.updateItemHeader(movieBriefData)
    }

    fun getCategoriesFromDb(categoryType: CategoryProvider.Category, action: (List<MovieBriefData>) -> Unit) {
        repo.getCategoryFromDB(categoryType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { action(it) },
                { Log.e("Interactor", it.message!!) }
            )
    }

    fun getSearchResultFromApi(searchQuery: String): Observable<List<MovieBriefData>> =
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

    fun getFavoriteMoviesFromDb(): Single<List<MovieBriefData>> = repo.getFavoriteMovies()
}
