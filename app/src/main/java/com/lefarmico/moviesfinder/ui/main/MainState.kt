package com.lefarmico.moviesfinder.ui.main

import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.ui.main.adapter.model.MovieDetailsModel

data class MainState(
    val isLoading: Boolean = false,
    val shownMovie: ShownMovie? = null,
    val toast: String? = null,
    val bottomSheetState: BottomSheetState = BottomSheetState.Hidden
) {
    sealed class BottomSheetState {
        object Hidden : BottomSheetState()
        object HalfExpanded : BottomSheetState()
        object Expanded : BottomSheetState()
    }
}

data class ShownMovie(
    val movieData: MovieDetailedData,
    val movieDetailsModelList: List<MovieDetailsModel>
)
