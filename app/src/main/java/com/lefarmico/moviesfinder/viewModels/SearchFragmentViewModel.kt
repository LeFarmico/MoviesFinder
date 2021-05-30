package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.ItemHeaderImpl
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SearchFragmentViewModel : ViewModel() {

    @Inject lateinit var interactor: Interactor

    init {
        App.appComponent.inject(this)
    }

    fun getSearchRequestResults(searchRequest: String): Observable<List<ItemHeaderImpl>> =
        interactor.getSearchResultFromApi(searchRequest)
}
