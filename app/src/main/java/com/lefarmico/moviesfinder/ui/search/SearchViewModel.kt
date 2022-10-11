package com.lefarmico.moviesfinder.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.data.manager.Interactor
import com.lefarmico.moviesfinder.ui.common.adapter.SearchAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val interactor: Interactor
) : ViewModel() {

    val searchSubject: PublishSubject<String> = PublishSubject.create()
    val searchAdapterLiveData = MutableLiveData<SearchAdapter>()

    fun searchViewObservable(): Observable<String> {
        return Observable.create { input ->
            searchSubject.subscribe {
                input.onNext(it)
            }
        }
    }

//    private fun getSearchRequestResults(searchRequest: String): Observable<List<MovieBriefData>> =
//        interactor.getSearchResultFromApi(searchRequest)
//
//    fun putSearchRequest(requestText: String) {
//        interactor.putSearchRequestToDb(requestText)
//    }

//    private fun result() {
//        Observable.create { input ->
//            searchSubject.subscribe {
//                input.onNext(it)
//            }
//        }
//            .debounce(1, TimeUnit.SECONDS)
//            .filter {
//                !it.isNullOrBlank()
//            }
//            .distinctUntilChanged()
//            .flatMap { requestText ->
//                getSearchRequestResults(requestText)
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { list ->
//                val adapter = SearchAdapter {
//                }.apply {
//                    items = list.map {
//                        SearchedItemData(SearchType.Search, it)
//                    }
//                }
//                searchAdapterLiveData.postValue(adapter)
//            }
//    }
}
