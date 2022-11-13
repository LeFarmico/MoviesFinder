package com.lefarmico.moviesfinder.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.manager.useCase.GetNowPlayingMovieBriefListUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetPopularMovieBriefListUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetTopRatedMovieBriefListUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetUpcomingMovieBriefListUseCase
import com.lefarmico.moviesfinder.ui.base.ErrorViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MoviesState(
    val isLoading: Boolean = false,
    val error: ErrorViewState? = null,
    val menuItemList: List<MenuItem> = emptyList()
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

            awaitAll(popularCategory, upcomingCategory, nowPlayingCategory, topRatedCategory)
                .merge()
                .collectLatest { resultData ->
                    menuItemList.add(resultData)
                    state.value = state.value?.copy(
                        isLoading = false,
                        menuItemList = menuItemList,
                        error = null
                    )
                }
        }
    }

    // TODO split by error type
    fun setErrorState(throwable: Throwable) {
        val error = ErrorViewState(
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
