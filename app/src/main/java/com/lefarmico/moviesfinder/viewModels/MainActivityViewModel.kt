package com.lefarmico.moviesfinder.viewModels

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.data.appEntity.ItemHeaderImpl
import com.lefarmico.moviesfinder.data.appEntity.Movie
import com.lefarmico.moviesfinder.data.appEntity.MovieItem
import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.fragments.ProfileFragment
import com.lefarmico.moviesfinder.fragments.SeriesFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val TAG = this.javaClass.canonicalName
    @Inject lateinit var interactor: Interactor
    var fragmentsList: List<Pair<String, Fragment>>
    val fragmentData = MutableLiveData<Pair<String, Fragment>>()
    val movieDetails = MutableLiveData<MovieItem>()
    init {
        App.appComponent.inject(this)
        fragmentsList = listOf(
            Pair("MovieFragment", MovieFragment()), // 0
            Pair("SeriesFragment", SeriesFragment()), // 1
            Pair("FavoritesFragment", ProfileFragment()), // 2
        )
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

    fun onItemClick(itemHeader: ItemHeader) {
        interactor.getMovieDetailsFromApi(itemHeader, itemHeader.itemId, this)
    }

    fun watchlistChanger(item: MovieItem, watchlistToggle: Boolean) {
        scope.launch {
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
            if (watchlistToggle) {
                interactor.putMovieDetails(item as Movie)
            } else {
                interactor.deleteMovieDetails(item as Movie)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared")
        scope.cancel()
    }
}
