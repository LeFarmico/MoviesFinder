package com.lefarmico.moviesfinder.ui.movies

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.data.manager.useCase.GetNowPlayingMovieBriefListUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetPopularMovieBriefListUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetTopRatedMovieBriefListUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetUpcomingMovieBriefListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

data class MoviesState(
    val isLoading: Boolean = false,
    val error: Error? = null,
    val menuItemList: List<MenuItem> = emptyList()
)

data class Error(
    @StringRes val errorTitle: Int,
    @StringRes val errorDescription: Int,
    @StringRes val errorButtonDescription: Int,
)

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovieBriefListUseCase,
    private val getUpcomingMovies: GetUpcomingMovieBriefListUseCase,
    private val getNowPlayingMovies: GetNowPlayingMovieBriefListUseCase,
    private val getTopRatedMovies: GetTopRatedMovieBriefListUseCase,
) : ViewModel() {

    val state = MutableLiveData(
        MoviesState(isLoading = true)
    )

    val cancellationExceptionHandle = CoroutineExceptionHandler { _, _ ->
        val error = Error(
            R.string.error_def_title,
            R.string.error_def_description,
            R.string.error_def_button
        )
        state.value = state.value?.copy(
            isLoading = false,
            error = error
        )
    }

    init {
        loadMoviesCategories()
    }

    fun loadMoviesCategories() {
        viewModelScope.launch {
            val popularCategory = async { getPopularMovies() }
            val upcomingCategory = async { getUpcomingMovies() }
            val nowPlayingCategory = async { getNowPlayingMovies() }
            val topRatedCategory = async { getTopRatedMovies() }

            val menuItemList: MutableList<MenuItem> = mutableListOf()

            // handle slow internet exception
            val result = withTimeout(1_000) {
                awaitAll(popularCategory, upcomingCategory, nowPlayingCategory, topRatedCategory)
            }

            result.merge().collectLatest { dataState ->
                when (dataState) {
                    State.Loading -> {
                        state.value = state.value?.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                    is State.Success -> {
                        menuItemList.add(dataState.data)
                        state.value = state.value?.copy(
                            isLoading = false,
                            menuItemList = menuItemList,
                            error = null
                        )
                    }
                    is State.Error -> {
                        val error = Error(
                            R.string.error_def_title,
                            R.string.error_def_description,
                            R.string.error_def_button
                        )
                        state.value = state.value?.copy(
                            isLoading = false,
                            error = error
                        )
                    }
                }
            }
        }
    }

    // TODO split by error type
    fun setErrorState(throwable: Throwable) {
        val error = Error(
            R.string.error_def_title,
            R.string.error_def_description,
            R.string.error_def_button
        )
        state.value = state.value?.copy(
            isLoading = false,
            error = error
        )
    }
}
