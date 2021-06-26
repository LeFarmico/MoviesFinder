package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.ItemHeader
import com.lefarmico.moviesfinder.data.appEntity.Movie
import com.lefarmico.moviesfinder.data.appEntity.MovieItem
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    private val TAG = this.javaClass.canonicalName
    @Inject lateinit var interactor: Interactor
    val movieDetails: BehaviorSubject<Movie>

    init {
        App.appComponent.inject(this)
        movieDetails = interactor.movieDetailsBehaviourSubject
    }

    fun onItemClick(itemHeader: ItemHeader) {
        interactor.putMovieFromApiToBehaviour(itemHeader)
    }

    fun watchlistHandler(item: MovieItem, watchlistToggle: Boolean) {
        Single.create<Boolean> {
            it.onSuccess(watchlistToggle)
        }
            .subscribeOn(Schedulers.io())
            .subscribe { isInWatchlist ->
                val updatedMovie = (item as Movie).copy(isWatchlist = isInWatchlist)
                val updatedHeader = updatedMovie.toItemHeaderImpl()
                interactor.updateItemHeaderInDb(updatedHeader as ItemHeader)
                if (watchlistToggle) {
                    interactor.putMovieDetailsToDb(updatedMovie)
                } else {
                    interactor.deleteMovieDetailsFromDb(updatedMovie)
                }
            }
    }
}
