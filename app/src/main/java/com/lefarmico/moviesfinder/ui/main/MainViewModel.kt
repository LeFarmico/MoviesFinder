package com.lefarmico.moviesfinder.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.manager.Interactor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val interactor: Interactor,
//    val saveMovieDetailedToDBUseCase: SaveMovieDetailedToDBUseCase,
//    val detailedToDBUseCase: SaveMovieDetailedToDBUseCase
) : ViewModel() {

    private var _shownMovie = MutableLiveData<MovieDetailedData>()
    val shownMovieLiveData get() = _shownMovie

    fun startObserveMovieDetailedFromChannel() {
        viewModelScope.launch {
            interactor.actionOnMovieDetailedInvoked {
                _shownMovie.value = it
            }
        }
    }
}
