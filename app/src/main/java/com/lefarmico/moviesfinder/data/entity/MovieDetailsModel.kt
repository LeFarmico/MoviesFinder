package com.lefarmico.moviesfinder.data.entity

import androidx.annotation.StringRes

sealed class MovieDetailsModel {

    data class RatingOverview(
        val usesRating: Float?,
        val userRating: Int?,
        val isWatchList: Boolean
    ) : MovieDetailsModel() {
        companion object
    }

    data class MovieInfoOverview(
        val genres: String,
        val length: String,
        val releaseDate: String
    ) : MovieDetailsModel() {
        companion object
    }

    data class HeaderAndTextExpandable(
        @StringRes val header: Int,
        val description: String
    ) : MovieDetailsModel() {
        companion object
    }

    data class CastAndCrew(
        val castList: List<MovieCastData>,
        val crewList: List<MovieCrewData>
    ) : MovieDetailsModel() {
        companion object
    }

    data class WhereToWatch(val providerList: List<MovieProviderData>?) : MovieDetailsModel() {
        companion object
    }

    data class Header(@StringRes val headerRes: Int) : MovieDetailsModel() {
        companion object
    }

    data class AddedMovie(val movieBriefData: MovieBriefData) : MovieDetailsModel() {
        companion object
    }
}
