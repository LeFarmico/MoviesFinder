package com.lefarmico.moviesfinder.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var _state = MutableLiveData(MainState())
    val state get() = _state
    private val currentState: MainState get() = state.value ?: MainState()

    fun cleanToast() {
        _state.value = currentState.copy(toast = null)
    }
}
