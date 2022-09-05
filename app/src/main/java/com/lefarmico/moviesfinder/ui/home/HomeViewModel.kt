package com.lefarmico.moviesfinder.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.http.response.State
import com.lefarmico.moviesfinder.data.manager.useCase.GetNowPlayingMovieBriefList
import com.lefarmico.moviesfinder.data.manager.useCase.GetPopularMovieBriefList
import com.lefarmico.moviesfinder.data.manager.useCase.GetTopRatedMovieBriefList
import com.lefarmico.moviesfinder.data.manager.useCase.GetUpcomingMovieBriefList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovieBriefList,
    private val getUpcomingMovies: GetUpcomingMovieBriefList,
    private val getNowPlayingMovies: GetNowPlayingMovieBriefList,
    private val getTopRatedMovies: GetTopRatedMovieBriefList,
) : ViewModel() {

    val state = MutableLiveData(
        HomeState(isLoading = true)
    )

    init {
        loadMoviesCategories()
    }

    private fun loadMoviesCategories() {
        viewModelScope.launch {
            val popularCategory = async {
                getPopularMovies.byPage(1)
            }
            val upcomingCategory = async {
                getUpcomingMovies.byPage(1)
            }
            val nowPlayingCategory = async {
                getNowPlayingMovies.byPage(1)
            }
            val topRatedCategory = async {
                getTopRatedMovies.byPage(1)
            }
            val menuItemList: MutableList<MenuItem> = mutableListOf()
            awaitAll(
                popularCategory,
                upcomingCategory,
                nowPlayingCategory,
                topRatedCategory
            ).merge().collect { dataState ->
                when (dataState) {
                    is State.Error -> {}
                    State.Loading -> {
                        state.value = state.value?.copy(isLoading = true)
                    }
                    is State.Success -> {
                        menuItemList.add(dataState.data)
                    }
                }
            }
            state.value = state.value?.copy(
                isLoading = false,
                menuItemList = menuItemList
            )
        }
    }
}
