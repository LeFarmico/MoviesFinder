package com.lefarmico.moviesfinder.data.entity

import androidx.annotation.StringRes

sealed class MovieDetailsAdapterModel {

    data class RatingOverview(
        val usesRating: Float?,
        val userRating: Int?,
        val isWatchList: Boolean
    ) : MovieDetailsAdapterModel() {
        companion object
    }

    data class MovieInfoOverviewAdapter(
        val genres: String,
        val length: String,
        val releaseDate: String
    ) : MovieDetailsAdapterModel() {
        companion object
    }

    data class HeaderAndTextExpandable(
        @StringRes val header: Int,
        val description: String
    ) : MovieDetailsAdapterModel() {
        companion object
    }

    data class CastAndCrewMovieDetailsAdapterModel(
        val castList: List<MovieCastData>,
        val crewList: List<MovieCrewData>
    ) : MovieDetailsAdapterModel() {
        companion object
    }

    data class WhereToWatch(val providerList: List<MovieProviderData>?) : MovieDetailsAdapterModel() {
        companion object
    }

    data class MovieWidgetAdapter(val movieBriefData: MovieBriefData) : MovieDetailsAdapterModel() {
        companion object
    }

    data class Header(@StringRes val headerRes: Int) : MovieDetailsAdapterModel() {
        companion object
    }
}
