package com.lefarmico.moviesfinder.viewModels

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.data.appEntity.ItemHeaderImpl
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

    suspend fun onItemClick(itemHeader: ItemHeader) {
        interactor.getMovieDetailsFromApi(itemHeader, itemHeader.itemId, this)
    }

    fun watchlistChanger(item: ItemHeader, watchlistToggle: Boolean) {
        val updatedItemHeader = ItemHeaderImpl(
            id = item.id,
            itemId = item.itemId,
            posterPath = item.posterPath,
            title = item.title,
            rating = item.rating,
            description = item.description,
            isWatchlist = watchlistToggle,
            yourRate = 0,
            releaseDate = item.releaseDate

        )
        interactor.updateItemHeader(updatedItemHeader)
    }
}
