package com.lefarmico.moviesfinder.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.data.manager.Interactor
import com.lefarmico.moviesfinder.data.manager.useCase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovieBriefList,
    private val getUpcomingMovies: GetUpcomingMovieBriefList,
    private val getNowPlayingMovies: GetNowPlayingMovieBriefList,
    private val getTopRatedMovies: GetTopRatedMovieBriefList,
    private val getMovieDetailed: GetMovieDetailedApiUseCase,
    private val interactor: Interactor
) : ViewModel() {

    val state = MutableLiveData(
        HomeState(isLoading = true)
    )

    init {
        loadMoviesCategories()
    }

    fun showMovieDetail(movieId: Int) {
        viewModelScope.launch {
            when (val movieDetailedDataState = getMovieDetailed(movieId)) {
                is State.Error -> throw movieDetailedDataState.exception
                is State.Success -> interactor.sendMovieDetailedToChannel(movieDetailedDataState.data)
                else -> throw IllegalStateException("State.Loading is not able in this function")
            }
        }
    }

    private fun loadMoviesCategories() {
        viewModelScope.launch {
            val popularCategory = async { getPopularMovies() }
            val upcomingCategory = async { getUpcomingMovies() }
            val nowPlayingCategory = async { getNowPlayingMovies() }
            val topRatedCategory = async { getTopRatedMovies() }
            val menuItemList: MutableList<MenuItem> = mutableListOf()
            awaitAll(
                popularCategory,
                upcomingCategory,
                nowPlayingCategory,
                topRatedCategory
            ).merge().collectLatest { dataState ->
                when (dataState) {
                    is State.Error -> {}
                    State.Loading -> {
                        state.value = state.value?.copy(isLoading = true)
                    }
                    is State.Success -> {
                        menuItemList.add(dataState.data)
                        state.value = state.value?.copy(
                            isLoading = false,
                            menuItemList = menuItemList
                        )
                    }
                }
            }
        }
    }
}
