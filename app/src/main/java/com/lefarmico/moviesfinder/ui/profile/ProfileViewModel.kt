package com.lefarmico.moviesfinder.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.data.manager.Interactor
import com.lefarmico.moviesfinder.ui.App
import com.lefarmico.moviesfinder.ui.common.adapter.WatchListAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

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
//                val adapter = WatchListAdapter().apply {
//                    setItems()
//                    item(listHeader)
//                }
//                itemsLiveData.postValue(adapter)
            }
    }
}
