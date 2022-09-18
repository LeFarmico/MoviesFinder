package com.lefarmico.moviesfinder.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.data.manager.Interactor
import com.lefarmico.moviesfinder.data.manager.useCase.DeleteMovieDetailedFromDBUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.SaveMovieDetailedToDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val interactor: Interactor,
    val saveMovieDetailedToDBUseCase: SaveMovieDetailedToDBUseCase,
    val deleteMovieDetailedFromDBUseCase: DeleteMovieDetailedFromDBUseCase
) : ViewModel() {

    private var _shownMovie = MutableLiveData<MovieDetailedData>()
    val shownMovieLiveData get() = _shownMovie

    private var _toast = MutableLiveData<String?>()
    val toastLiveData get() = _toast

    fun startObserveMovieDetailedFromChannel() {
        viewModelScope.launch {
            interactor.actionOnMovieDetailedInvoked {
                _shownMovie.value = it
            }
        }
    }

    fun tryToSaveMovieToWatchlist() {
        viewModelScope.launch {
            val currentMovieDetailedData = _shownMovie.value
            currentMovieDetailedData?.copy(isWatchlist = true)?.let {
                saveMovieDetailedToDBUseCase(it).collectLatest { state ->
                    when (state) {
                        is State.Error -> _toast.value = "${state.exception.message}"
                        State.Loading -> {}
                        is State.Success -> {
                            // TODO return message id from resources
                            _toast.value = "The movie successfully saved to watchlist"
                        }
                    }
                }
            }
        }
    }

    fun tryToRemoveMovieFromWatchlist() {
        viewModelScope.launch {
            val currentMovieDetailedData = _shownMovie.value
            currentMovieDetailedData?.copy(isWatchlist = false)?.let {
                deleteMovieDetailedFromDBUseCase(it).collectLatest { state ->
                    when (state) {
                        is State.Error -> _toast.value = "${state.exception.message}"
                        State.Loading -> {}
                        is State.Success -> {
                            // TODO return message id from resources
                            _toast.value = "The movie successfully deleted from watchlist"
                        }
                    }
                }
            }
        }
    }

    fun cleanToast() {
        _toast.value = null
    }
}
