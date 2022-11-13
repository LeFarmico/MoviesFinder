package com.lefarmico.moviesfinder.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.entity.MovieDetailsModel
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.data.http.response.entity.onError
import com.lefarmico.moviesfinder.data.http.response.entity.onLoading
import com.lefarmico.moviesfinder.data.http.response.entity.onSuccess
import com.lefarmico.moviesfinder.data.manager.useCase.DeleteMovieDetailedFromDBUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetMovieDetailedApiUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.SaveMovieDetailedToDBUseCase
import com.lefarmico.moviesfinder.utils.SingleLiveEvent
import com.lefarmico.moviesfinder.utils.extension.roundTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieFragmentState(
    val isLoading: Boolean = false,
    val movieData: MovieDetailedData? = null,
    val movieDetailsModelList: List<MovieDetailsModel> = listOf()
)

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val saveMovieDetailedToDBUseCase: SaveMovieDetailedToDBUseCase,
    private val deleteMovieDetailedFromDBUseCase: DeleteMovieDetailedFromDBUseCase,
    private val getMovieDetailed: GetMovieDetailedApiUseCase
) : ViewModel() {

    private var _state = MutableLiveData<MovieFragmentState>()
    val state: LiveData<MovieFragmentState> get() = _state
    private val currentState: MovieFragmentState get() = state.value ?: MovieFragmentState()

    private val _notificationEvent = SingleLiveEvent<String>()
    val event: LiveData<String> = _notificationEvent

    fun launchMovieDetailed(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailed(movieId).collectLatest { state ->
                state.apply {
                    onSuccess { movieDetailed ->
                        val movieDetailsModelList = mutableListOf<MovieDetailsModel>()
                        // TODO add builder
                        movieDetailsModelList.add(
                            MovieDetailsModel.MovieInfoOverview(
                                genres = movieDetailed.genres.reduce { acc, s -> "$acc / $s" },
                                length = "Length: ${movieDetailed.length} min",
                                releaseDate = movieDetailed.releaseDate
                            )
                        )
                        movieDetailsModelList.add(
                            MovieDetailsModel.RatingOverview(
                                usesRating = movieDetailed.rating.roundTo(1),
                                userRating = movieDetailed.yourRate,
                                isWatchList = movieDetailed.isWatchlist
                            )
                        )
                        if (movieDetailed.watchMovieProviderData != null &&
                            movieDetailed.watchMovieProviderData.isEmpty()
                        ) {
                            movieDetailsModelList.add(
                                MovieDetailsModel.WhereToWatch(
                                    providerList = movieDetailed.watchMovieProviderData
                                )
                            )
                        }
                        movieDetailsModelList.add(
                            MovieDetailsModel.HeaderAndTextExpandable(
                                header = R.string.storyline,
                                description = movieDetailed.description
                            )
                        )
                        movieDetailsModelList.add(
                            MovieDetailsModel.Header(R.string.cast)
                        )
                        movieDetailsModelList.add(
                            MovieDetailsModel.CastAndCrew(
                                castList = movieDetailed.actors ?: listOf(),
                                crewList = movieDetailed.directors ?: listOf()
                            )
                        )
                        if (!movieDetailed.recommendations.isNullOrEmpty()) {
                            val addedMovieList = movieDetailed.recommendations.map { MovieDetailsModel.AddedMovie(it) }
                            movieDetailsModelList.add(
                                MovieDetailsModel.Header(
                                    R.string.you_may_also_like
                                )
                            )
                            movieDetailsModelList.addAll(addedMovieList)
                        }
                        _state.value = currentState.copy(
                            isLoading = false,
                            movieData = movieDetailed,
                            movieDetailsModelList = movieDetailsModelList
                        )
                    }
                    onLoading {
                        // TODO Not yet implemented
                    }
                    onError {
                        // TODO Not yet implemented
                    }
                }
            }
        }
    }

    fun saveMovieToWatchlist() {
        viewModelScope.launch {
            currentState.movieData?.let {
                saveMovieDetailedToDBUseCase(it.copy(isWatchlist = true)).collectLatest { state ->
                    when (state) {
                        is State.Error -> _notificationEvent.value = "${state.exception.message}"
                        State.Loading -> {}
                        is State.Success -> {
                            // TODO return message id from resources
                            _notificationEvent.value = "The movie successfully saved to watchlist"
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
                        is State.Error -> _notificationEvent.value = "${state.exception.message}"
                        State.Loading -> {}
                        is State.Success -> {
                            // TODO return message id from resources
                            _notificationEvent.value = "The movie successfully deleted to watchlist"
                        }
                    }
                }
            }
        }
    }
}
