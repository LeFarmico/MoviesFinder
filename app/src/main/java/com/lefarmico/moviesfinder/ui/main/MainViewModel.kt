package com.lefarmico.moviesfinder.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.data.manager.Interactor
import com.lefarmico.moviesfinder.data.manager.useCase.DeleteMovieDetailedFromDBUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetMovieDetailedApiUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetRecommendationsMovieBriefListUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.SaveMovieDetailedToDBUseCase
import com.lefarmico.moviesfinder.ui.main.adapter.model.MovieDetailsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interactor: Interactor,
    private val saveMovieDetailedToDBUseCase: SaveMovieDetailedToDBUseCase,
    private val deleteMovieDetailedFromDBUseCase: DeleteMovieDetailedFromDBUseCase,
    private val getMovieDetailed: GetMovieDetailedApiUseCase,
    private val getRecommendations: GetRecommendationsMovieBriefListUseCase,
) : ViewModel() {

    private var _state = MutableLiveData<MainState>()
    val state get() = _state
    private val currentState: MainState = state.value ?: MainState()

    fun startObserveMovieDetailedFromChannel() {
        viewModelScope.launch {
            interactor.actionOnMovieDetailedInvoked { movieId ->

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
                            MovieDetailsModel.CastAndCrewMovieDetailsModel(
                                castHeader = R.string.cast,
                                crewHeader = R.string.crew,
                                castList = moveState.data.actors ?: listOf(),
                                crewList = moveState.data.directors ?: listOf()
                            )
                        )
                        val recommendationState = recommendationsState.await()
                        recommendationState.collectLatest { recState ->
                            when (recState) {
                                is State.Success -> {
                                    if (recState.data.isNotEmpty()) {
                                        val movieLargeModelList = recState.data.map { MovieDetailsModel.MovieLargeModel(it) }
                                        movieDetailsModelList.add(
                                            MovieDetailsModel.Header(
                                                R.string.you_may_also_like
                                            )
                                        )
                                        movieDetailsModelList.addAll(movieLargeModelList)
                                    }
                                }
                                is State.Error -> {}
                                State.Loading -> {}
                            }
                            val shownMovie = ShownMovie(
                                moveState.data,
                                movieDetailsModelList
                            )
                            _state.value = currentState.copy(shownMovie = shownMovie)
                        }
                    }
                    else -> throw IllegalStateException("State.Loading is not able in this function")
                }
            }
        }
    }

    fun tryToSaveMovieToWatchlist() {
//        viewModelScope.launch {
//            currentState.shownMovie?.copy(isWatchlist = true)?.let {
//                saveMovieDetailedToDBUseCase(it).collectLatest { state ->
//                    when (state) {
//                        is State.Error -> _state.value = currentState.copy(toast = "${state.exception.message}")
//                        State.Loading -> {}
//                        is State.Success -> {
//                            // TODO return message id from resources
//                            _state.value = currentState.copy(toast = "The movie successfully saved to watchlist")
//                        }
//                    }
//                }
//            }
//        }
    }

    fun tryToRemoveMovieFromWatchlist() {
//        viewModelScope.launch {
//            currentState.shownMovie?.copy(isWatchlist = false)?.let {
//                deleteMovieDetailedFromDBUseCase(it).collectLatest { state ->
//                    when (state) {
//                        is State.Error -> _state.value = currentState.copy(toast = "${state.exception.message}")
//                        State.Loading -> {}
//                        is State.Success -> {
//                            // TODO return message id from resources
//                            _state.value = currentState.copy(toast = "The movie successfully deleted to watchlist")
//                        }
//                    }
//                }
//            }
//        }
    }

    fun cleanToast() {
        _state.value = currentState.copy(toast = null)
    }
}
