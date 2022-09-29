package com.lefarmico.moviesfinder.ui.main.adapter.model

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.entity.MovieCastData
import com.lefarmico.moviesfinder.data.entity.MovieCrewData
import com.lefarmico.moviesfinder.data.entity.MovieProviderData

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

    data class CastAndCrewMovieDetailsModel(
        @StringRes val castHeader: Int,
        @StringRes val crewHeader: Int,
        val castList: List<MovieCastData>,
        val crewList: List<MovieCrewData>
    ) : MovieDetailsModel() {
        companion object
    }

    data class WhereToWatch(val providerList: List<MovieProviderData>?) : MovieDetailsModel() {
        companion object
    }

    data class MovieLargeModel(val movieBriefData: MovieBriefData) : MovieDetailsModel() {
        companion object
    }

    data class Header(@StringRes val headerRes: Int) : MovieDetailsModel() {
        companion object
    }
}

class WidgetDiffUtilCallback : DiffUtil.ItemCallback<MovieDetailsModel>() {
    override fun areItemsTheSame(oldItem: MovieDetailsModel, newItem: MovieDetailsModel): Boolean =
        oldItem::class == newItem::class && oldItem === newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MovieDetailsModel, newItem: MovieDetailsModel): Boolean =
        oldItem == newItem
}
