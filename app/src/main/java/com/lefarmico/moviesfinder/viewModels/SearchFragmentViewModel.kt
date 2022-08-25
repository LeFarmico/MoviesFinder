package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.adapter.SearchAdapter
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.Header
import com.lefarmico.moviesfinder.data.appEntity.SearchType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragmentViewModel : ViewModel() {

    @Inject lateinit var interactor: Interactor
    val searchSubject: PublishSubject<String> = PublishSubject.create()
    val searchAdapterLiveData = MutableLiveData<SearchAdapter>()

    init {
        App.appComponent.inject(this)
        result()
    }

    fun searchViewObservable(): Observable<String> {
        return Observable.create { input ->
            searchSubject.subscribe {
                input.onNext(it)
            }
        }
    }

    private fun getSearchRequestResults(searchRequest: String): Observable<List<Header>> =
        interactor.getSearchResultFromApi(searchRequest)

    fun putSearchRequest(requestText: String) {
        interactor.putSearchRequestToDb(requestText)
    }

    private fun result() {
        Observable.create<String> { input ->
            searchSubject.subscribe {
                input.onNext(it)
            }
        }
            .debounce(1, TimeUnit.SECONDS)
            .filter {
                !it.isNullOrBlank()
            }
            .distinctUntilChanged()
            .flatMap { requestText ->
                getSearchRequestResults(requestText)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                val adapter = SearchAdapter()
                adapter.setSearchItems(list, SearchType.SEARCH)
                searchAdapterLiveData.postValue(adapter)
            }
    }
}
