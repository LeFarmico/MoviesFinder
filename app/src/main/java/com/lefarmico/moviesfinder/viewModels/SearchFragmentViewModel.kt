package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.Header
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class SearchFragmentViewModel : ViewModel() {

    @Inject lateinit var interactor: Interactor
    val searchSubject: PublishSubject<String> = PublishSubject.create()

    init {
        App.appComponent.inject(this)
    }

    fun searchViewObservable(): Observable<String> {
        return Observable.create { input ->
            searchSubject.subscribe {
                input.onNext(it)
            }
        }
    }

    fun getSearchRequestResults(searchRequest: String): Observable<List<Header>> =
        interactor.getSearchResultFromApi(searchRequest)

    fun putSearchRequest(requestText: String) {
        interactor.putSearchRequestToDb(requestText)
    }

    fun getSearchRequests(): Single<List<String>> = interactor.getLastedSearchRequests()
}
