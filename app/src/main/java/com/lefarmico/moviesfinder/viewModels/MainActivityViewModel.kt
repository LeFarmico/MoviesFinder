package com.lefarmico.moviesfinder.viewModels

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel() : ViewModel() {

    val fragmentData = MutableLiveData<Pair<String, Fragment>>()

    fun postFragmentData(fragmentPair: Pair<String, Fragment>) {
        try {
            fragmentData.postValue(fragmentPair)
        } catch (e: IllegalStateException) {
            return
        }
    }
}
