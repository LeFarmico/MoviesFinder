package com.lefarmico.moviesfinder.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.data.manager.useCase.DeleteMovieDetailedFromDBUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetMovieDetailedApiUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetRecommendationsMovieBriefListUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.SaveMovieDetailedToDBUseCase
import com.lefarmico.moviesfinder.ui.base.BaseViewModel
import com.lefarmico.moviesfinder.ui.main.adapter.model.MovieDetailsModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class MovieViewModel(
    private val saveMovieDetailedToDBUseCase: SaveMovieDetailedToDBUseCase,
    private val deleteMovieDetailedFromDBUseCase: DeleteMovieDetailedFromDBUseCase,
    private val getMovieDetailed: GetMovieDetailedApiUseCase,
    private val getRecommendations: GetRecommendationsMovieBriefListUseCase,
) : BaseViewModel() {

    private var _state = MutableLiveData<MovieFragmentState>()
    val state get() = _state
    private val currentState: MovieFragmentState get() = state.value ?: MovieFragmentState()

    fun launchMovieDetailed(movieId: Int) {
        viewModelScope.launch {
            val movieDetailedState = async {
                getMovieDetailed(movieId)
            }
            val recommendationsState = async {
                getRecommendations(movieId)
            }
            val movieDetailsModelList = mutableListOf<MovieDetailsModel>()
            when (val moveState = movieDetailedState.await()) {
                is State.Error -> throw moveState.exception
                is State.Success -> {
                    // TODO add builder
                    movieDetailsModelList.add(
                        MovieDetailsModel.MovieInfoOverview(
                            genres = moveState.data.genres.reduce { acc, s -> "$acc / $s" },
                            length = "Length: ${moveState.data.length} min",
                            releaseDate = moveState.data.releaseDate
                        )
                    )
                    movieDetailsModelList.add(
                        MovieDetailsModel.RatingOverview(
                            usesRating = moveState.data.rating,
                            userRating = moveState.data.yourRate,
                            isWatchList = moveState.data.isWatchlist
                        )
                    )
                    if (moveState.data.watchMovieProviderData != null &&
                        moveState.data.watchMovieProviderData.isEmpty()
                    ) {
                        movieDetailsModelList.add(
                            MovieDetailsModel.WhereToWatch(
                                providerList = moveState.data.watchMovieProviderData
                            )
                        )
                    }
                    movieDetailsModelList.add(
                        MovieDetailsModel.HeaderAndTextExpandable(
                            header = R.string.storyline,
                            description = moveState.data.description
                        )
                    )
                    movieDetailsModelList.add(
                        MovieDetailsModel.Header(R.string.cast)
                    )
                    movieDetailsModelList.add(
                        MovieDetailsModel.CastAndCrewMovieDetailsModel(
                            castList = moveState.data.actors ?: listOf(),
                            crewList = moveState.data.directors ?: listOf()
                        )
                    )
                    _state.value = currentState.copy(
                        movieData = moveState.data,
                        movieDetailsModelList = movieDetailsModelList,
                        bottomSheetState = MovieFragmentState.BottomSheetState.HalfExpanded
                    )
                    val recommendationState = recommendationsState.await()
                    recommendationState.collectLatest { recState ->
                        val recommendationModelList = mutableListOf<MovieDetailsModel>()
                        when (recState) {
                            is State.Success -> {
                                if (recState.data.isNotEmpty()) {
                                    val movieLargeModelList = recState.data.map { MovieDetailsModel.MovieLargeModel(it) }
                                    recommendationModelList.add(
                                        MovieDetailsModel.Header(
                                            R.string.you_may_also_like
                                        )
                                    )
                                    recommendationModelList.addAll(movieLargeModelList)
                                }
                            }
                            is State.Error -> {}
                            State.Loading -> {}
                        }
                        _state.value = currentState.copy(
                            movieData = moveState.data,
                            movieDetailsModelList = movieDetailsModelList + recommendationModelList
                        )
                    }
                }
                else -> throw IllegalStateException("State.Loading is not able in this function")
            }
        }
    }

    fun saveMovieToWatchlist() {
        viewModelScope.launch {
            currentState.movieData?.let {
                saveMovieDetailedToDBUseCase(it.copy(isWatchlist = true)).collectLatest { state ->
                    when (state) {
                        is State.Error -> _state.value = currentState.copy(toast = "${state.exception.message}")
                        State.Loading -> {}
                        is State.Success -> {
                            // TODO return message id from resources
                            _state.value = currentState.copy(toast = "The movie successfully saved to watchlist")
                        }
                    }
                }
            }
        }
    }

    fun removeMovieFromWatchlist() {
        viewModelScope.launch {
            currentState.movieData?.let {
                deleteMovieDetailedFromDBUseCase(it.copy(isWatchlist = false)).collectLatest { state ->
                    when (state) {
                        is State.Error -> _state.value = currentState.copy(toast = "${state.exception.message}")
                        State.Loading -> {}
                        is State.Success -> {
                            // TODO return message id from resources
                            _state.value = currentState.copy(toast = "The movie successfully deleted to watchlist")
                        }
                    }
                }
            }
        }
    }

    fun cleanToast() {
        _state.value = currentState.copy(toast = null)
    }

    fun setBottomSheetState(bottomSheetState: MovieFragmentState.BottomSheetState) {
        _state.value = currentState.copy(bottomSheetState = bottomSheetState)
    }
}
