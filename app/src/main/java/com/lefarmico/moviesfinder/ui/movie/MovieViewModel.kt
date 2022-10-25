package com.lefarmico.moviesfinder.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MovieDetailsAdapterModel
import com.lefarmico.moviesfinder.data.http.response.entity.State
import com.lefarmico.moviesfinder.data.manager.useCase.DeleteMovieDetailedFromDBUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetMovieDetailedApiUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetRecommendationsMovieBriefListUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.SaveMovieDetailedToDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val saveMovieDetailedToDBUseCase: SaveMovieDetailedToDBUseCase,
    private val deleteMovieDetailedFromDBUseCase: DeleteMovieDetailedFromDBUseCase,
    private val getMovieDetailed: GetMovieDetailedApiUseCase,
    private val getRecommendations: GetRecommendationsMovieBriefListUseCase,
) : ViewModel() {

    private var _state = MutableLiveData<MovieFragmentState>()
    val state: LiveData<MovieFragmentState> get() = _state
    private val currentState: MovieFragmentState get() = state.value ?: MovieFragmentState()

    fun launchMovieDetailed(movieId: Int) {
        viewModelScope.launch {
            val movieDetailedState = async {
                getMovieDetailed(movieId)
            }
            val recommendationsState = async {
                getRecommendations(movieId)
            }
            val movieDetailsAdapterModelList = mutableListOf<MovieDetailsAdapterModel>()
            when (val moveState = movieDetailedState.await()) {
                is State.Error -> throw moveState.exception
                is State.Success -> {
                    // TODO add builder
                    movieDetailsAdapterModelList.add(
                        MovieDetailsAdapterModel.MovieInfoOverviewAdapter(
                            genres = moveState.data.genres.reduce { acc, s -> "$acc / $s" },
                            length = "Length: ${moveState.data.length} min",
                            releaseDate = moveState.data.releaseDate
                        )
                    )
                    movieDetailsAdapterModelList.add(
                        MovieDetailsAdapterModel.RatingOverview(
                            usesRating = moveState.data.rating,
                            userRating = moveState.data.yourRate,
                            isWatchList = moveState.data.isWatchlist
                        )
                    )
                    if (moveState.data.watchMovieProviderData != null &&
                        moveState.data.watchMovieProviderData.isEmpty()
                    ) {
                        movieDetailsAdapterModelList.add(
                            MovieDetailsAdapterModel.WhereToWatch(
                                providerList = moveState.data.watchMovieProviderData
                            )
                        )
                    }
                    movieDetailsAdapterModelList.add(
                        MovieDetailsAdapterModel.HeaderAndTextExpandable(
                            header = R.string.storyline,
                            description = moveState.data.description
                        )
                    )
                    movieDetailsAdapterModelList.add(
                        MovieDetailsAdapterModel.Header(R.string.cast)
                    )
                    movieDetailsAdapterModelList.add(
                        MovieDetailsAdapterModel.CastAndCrewMovieDetailsAdapterModel(
                            castList = moveState.data.actors ?: listOf(),
                            crewList = moveState.data.directors ?: listOf()
                        )
                    )
                    _state.value = currentState.copy(
                        movieData = moveState.data,
                        movieDetailsAdapterModelList = movieDetailsAdapterModelList,
                        bottomSheetState = MovieFragmentState.BottomSheetState.HalfExpanded
                    )
                    val recommendationState = recommendationsState.await()
                    recommendationState.collectLatest { recState ->
                        val recommendationModelList = mutableListOf<MovieDetailsAdapterModel>()
                        when (recState) {
                            is State.Success -> {
                                if (recState.data.isNotEmpty()) {
                                    val movieWidgetList = recState.data.map { MovieDetailsAdapterModel.MovieWidgetAdapter(it) }
                                    recommendationModelList.add(
                                        MovieDetailsAdapterModel.Header(
                                            R.string.you_may_also_like
                                        )
                                    )
                                    recommendationModelList.addAll(movieWidgetList)
                                }
                            }
                            is State.Error -> {}
                            State.Loading -> {}
                        }
                        _state.value = currentState.copy(
                            movieData = moveState.data,
                            movieDetailsAdapterModelList = movieDetailsAdapterModelList + recommendationModelList
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
