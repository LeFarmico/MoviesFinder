package com.lefarmico.moviesfinder.ui.movie

import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.entity.MovieDetailsAdapterModel

data class MovieFragmentState(
    val isLoading: Boolean = false,
    val movieData: MovieDetailedData? = null,
    val movieDetailsAdapterModelList: List<MovieDetailsAdapterModel> = listOf(),
    val toast: String? = null,
    val bottomSheetState: BottomSheetState = BottomSheetState.HalfExpanded
) {
    sealed class BottomSheetState {
        object HalfExpanded : BottomSheetState()
        object Expanded : BottomSheetState()
    }
}
