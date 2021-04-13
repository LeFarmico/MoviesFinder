package com.lefarmico.moviesfinder.viewModels

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.data.appEntity.MovieItem
import javax.inject.Inject

class MainActivityViewModel() : ViewModel() {

    @Inject lateinit var interactor: Interactor
    val fragmentData = MutableLiveData<Pair<String, Fragment>>()
    val movieDetails = MutableLiveData<MovieItem>()
    init {
        App.appComponent.inject(this)
    }
    fun postFragmentData(fragmentPair: Pair<String, Fragment>) {
        try {
            fragmentData.postValue(fragmentPair)
        } catch (e: IllegalStateException) {
            return
        }
    }
    fun showItemDetails(movieItem: MovieItem) {
        movieDetails.postValue(movieItem)
    }
    fun onFailureItemDetails(@StringRes errorText: Int) {
        // TODO: обработать onFailure()
        return
    }

    fun onItemClick(itemHeader: ItemHeader) {
        interactor.getMovieDetailsFromApi(itemHeader, itemHeader.itemId, this)
    }
}
