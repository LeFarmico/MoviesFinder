package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.entity.MovieData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    @Inject lateinit var interactor: Interactor
    val movieDataDetails: BehaviorSubject<MovieData>

    init {
        App.appComponent.inject(this)
        movieDataDetails = interactor.movieDataDetailsBehaviourSubject
    }

    fun onItemClick(movieBriefData: MovieBriefData) {
        interactor.putMovieFromApiToBehaviour(movieBriefData)
    }

    fun watchlistHandler(item: MovieData, watchlistToggle: Boolean) {
        Single.create<Boolean> {
            it.onSuccess(watchlistToggle)
        }
            .subscribeOn(Schedulers.io())
            .subscribe { isInWatchlist ->
                val updatedMovieData = (item).copy(isWatchlist = isInWatchlist)
                val updatedHeader = updatedMovieData.toItemHeaderImpl()
                interactor.updateItemHeaderInDb(updatedHeader)
                if (watchlistToggle) {
                    interactor.putMovieDetailsToDb(updatedMovieData)
                } else {
                    interactor.deleteMovieDetailsFromDb(updatedMovieData)
                }
            }
    }
}
