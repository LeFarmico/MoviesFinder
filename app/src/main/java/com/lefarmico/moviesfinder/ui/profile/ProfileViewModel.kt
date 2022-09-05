package com.lefarmico.moviesfinder.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.data.manager.Interactor
import com.lefarmico.moviesfinder.ui.common.adapter.WatchListAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val interactor: Interactor
) : ViewModel() {

    var itemsLiveData = MutableLiveData<WatchListAdapter>()
    var watchedStatsLiveData = MutableLiveData<Int>()
    var watchlistStatsLiveData = MutableLiveData<Int>()
}
