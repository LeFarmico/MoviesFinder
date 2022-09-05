package com.lefarmico.moviesfinder.data.manager

import com.lefarmico.moviesfinder.data.http.request.TmdbApi
import com.lefarmico.moviesfinder.ui.common.provider.PreferenceProvider
import javax.inject.Inject

class Interactor @Inject constructor(
    private val repo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferenceProvider: PreferenceProvider
) {

//    val loadingState: BehaviorSubject<Boolean> = BehaviorSubject.create()
//    val movieDataDetailsBehaviourSubject: BehaviorSubject<MovieData> = BehaviorSubject.create()
//
//    fun getMovieBriefListFromApi(categoryDataType: CategoryProvider.CategoryData, page: Int) {
//        loadingState.onNext(true)
//        retrofitService.getMovies(categoryDataType.categoryRequestTitle, API_KEY, "en-US", page)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .map {
//                // TODO: normal mapper
//                Converter.convertApiListToDTOList(it.tmdbMovie)
//            }
//            .doOnNext { movieBriefList ->
//                loadingState.onNext(false)
//            }
//            .subscribe()
//    }
//
//    fun putMovieFromApiToBehaviour(movieBriefData: MovieBriefData) {
//        retrofitService.getMovieDetails(movieBriefData.itemId, API_KEY, "en-US", "watch/providers,credits")
//            .subscribeOn(Schedulers.io())
//            .map {
//                Converter.convertApiMovieDetailsToDTOItem(
//                    movieBriefData,
//                    preferenceProvider.getCurrentCountry(),
//                    it
//                )
//            }.subscribeBy(
//                onNext = { movie ->
//                    movieDataDetailsBehaviourSubject.onNext(movie)
//                },
//                onError = {
//                    return@subscribeBy
//                }
//            )
//    }
//    fun updateMoviesFromApi(categoryData: CategoryProvider.CategoryData, page: Int, paginate: (MutableList<MovieBriefData>) -> Unit) {
//        retrofitService.getMovies(categoryData.categoryRequestTitle, API_KEY, "en-US", page)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .map {
//                Converter.convertApiListToDTOList(it.tmdbMovie).toMutableList()
//            }.subscribeBy(
//                onError = { Log.e("Interactor", it.message!!) },
//                onNext = { itemList ->
//                    paginate(itemList)
//                }
//            )
//    }
//
//    fun putMovieDetailsToDb(movieData: MovieData) {
//        repo.putMovieToDb(movieData)
//    }
//
//    fun deleteMovieDetailsFromDb(movieData: MovieData) {
//        repo.deleteMovieFromDB(movieData)
//    }
//
//    fun updateItemHeaderInDb(movieBriefData: MovieBriefData) {
//        repo.updateItemHeader(movieBriefData)
//    }
//
//    fun getCategoriesFromDb(categoryDataType: CategoryProvider.CategoryData): Single<List<MovieBriefData>> {
//        return repo.getCategoryFromDB(categoryDataType)
//    }
//
//    fun getSearchResultFromApi(searchQuery: String): Observable<List<MovieBriefData>> =
//        retrofitService.getMovieFromSearch(API_KEY, "en-US", searchQuery, 1)
//            .subscribeOn(Schedulers.io())
//            .map { result ->
//                Converter.convertApiListToDTOList(result.tmdbMovie)
//            }
//
//    fun getLastedSearchRequestsFromDb(): Single<List<String>> = repo.getSearchRequests()
//
//    fun putSearchRequestToDb(requestText: String) {
//        Single.create<String> {
//            it.onSuccess(requestText)
//        }
//            .subscribeOn(Schedulers.io())
//            .subscribe { request ->
//                repo.putSearchRequestToDB(request)
//            }
//    }
//
//    fun getFavoriteMoviesFromDb(): Single<List<MovieBriefData>> = repo.getFavoriteMovies()
}
