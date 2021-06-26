package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.adapters.WatchListAdapter
import com.lefarmico.moviesfinder.data.Interactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ProfileFragmentViewModel : ViewModel() {

    @Inject lateinit var interactor: Interactor
    var itemsLiveData = MutableLiveData<WatchListAdapter>()
    var watchedStatsLiveData = MutableLiveData<Int>()
    var watchlistStatsLiveData = MutableLiveData<Int>()

    init {
        App.appComponent.inject(this)
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        interactor.getFavoriteMoviesFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { listHeader ->
                watchlistStatsLiveData.postValue(listHeader.size)
                val adapter = WatchListAdapter().apply {
                    setItems(listHeader)
                }
                itemsLiveData.postValue(adapter)
            }
    }
}
