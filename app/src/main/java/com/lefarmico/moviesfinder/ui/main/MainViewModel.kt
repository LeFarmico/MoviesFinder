package com.lefarmico.moviesfinder.ui.main

import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.data.manager.Interactor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val interactor: Interactor
) : ViewModel() {

//    val movieDataDetails: BehaviorSubject<MovieData> = interactor.movieDataDetailsBehaviourSubject
//
//    fun onItemClick(movieBriefData: MovieBriefData) {
//        interactor.putMovieFromApiToBehaviour(movieBriefData)
//    }
//
//    fun watchlistHandler(item: MovieData, watchlistToggle: Boolean) {
//        Single.create<Boolean> {
//            it.onSuccess(watchlistToggle)
//        }
//            .subscribeOn(Schedulers.io())
//            .subscribe { isInWatchlist ->
//                val updatedMovieData = (item).copy(isWatchlist = isInWatchlist)
//                val updatedHeader = updatedMovieData.toItemHeaderImpl()
//                interactor.updateItemHeaderInDb(updatedHeader)
//                if (watchlistToggle) {
//                    interactor.putMovieDetailsToDb(updatedMovieData)
//                } else {
//                    interactor.deleteMovieDetailsFromDb(updatedMovieData)
//                }
//            }
//    }
}
