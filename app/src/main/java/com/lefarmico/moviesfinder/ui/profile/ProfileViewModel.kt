package com.lefarmico.moviesfinder.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.data.manager.useCase.GetWatchListMovieDetailedFromDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getWatchListMovieDetailedFromDBUseCase: GetWatchListMovieDetailedFromDBUseCase
) : ViewModel() {

    private val _state = MutableLiveData<List<MovieDetailedData>>()
    val state: LiveData<List<MovieDetailedData>> get() = _state

    fun getWatchlistMovies() {
        viewModelScope.launch {
            getWatchListMovieDetailedFromDBUseCase().collectLatest { state ->
                when (state) {
                    is State.Error -> {}
                    State.Loading -> {}
                    is State.Success -> {
                        _state.value = state.data!!
                    }
                }
            }
        }
    }
}
