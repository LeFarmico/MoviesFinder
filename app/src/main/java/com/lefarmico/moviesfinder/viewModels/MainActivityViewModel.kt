package com.lefarmico.moviesfinder.viewModels

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.data.appEntity.Movie
import com.lefarmico.moviesfinder.data.appEntity.MovieItem
import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.fragments.ProfileFragment
import com.lefarmico.moviesfinder.fragments.SeriesFragment
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    private val TAG = this.javaClass.canonicalName
    @Inject lateinit var interactor: Interactor
    var fragmentsList: List<Pair<String, Fragment>>
    val fragmentLiveData = MutableLiveData<Pair<String, Fragment>>()
    val movieDetails: BehaviorSubject<Movie>

    init {
        App.appComponent.inject(this)
        fragmentsList = listOf(
            Pair("MovieFragment", MovieFragment()), // 0
            Pair("SeriesFragment", SeriesFragment()), // 1
            Pair("FavoritesFragment", ProfileFragment()), // 2
        )
        movieDetails = interactor.movieDetailsBehaviourSubject
    }
    fun postFragment(fragmentPair: Pair<String, Fragment>) {
        try {
            fragmentLiveData.postValue(fragmentPair)
        } catch (e: IllegalStateException) {
            return
        }
    }
    fun showItemDetails(movieItem: MovieItem) {
        movieDetails.onNext(movieItem as Movie)
    }

    fun onItemClick(itemHeader: ItemHeader) {
        interactor.getMovieDetailsFromApi(itemHeader, itemHeader.itemId, this)
    }

    fun watchlistHandler(item: MovieItem, watchlistToggle: Boolean) {
        Single.create<Boolean> {
            it.onSuccess(watchlistToggle)
        }
            .subscribeOn(Schedulers.io())
            .subscribe { isInWatchlist ->
                val updatedMovie = (item as Movie).copy(isWatchlist = isInWatchlist).toItemHeaderImpl()
                interactor.updateItemHeader(updatedMovie as ItemHeader)
                if (watchlistToggle) {
                    interactor.putMovieDetailsToDb(item)
                } else {
                    interactor.deleteMovieDetails(item)
                }
            }
    }
}
