package com.lefarmico.moviesfinder.ui.common.adapter.widgetAdapter.widget

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.entity.MovieCastData
import com.lefarmico.moviesfinder.data.entity.MovieCrewData
import com.lefarmico.moviesfinder.data.entity.MovieProviderData

sealed class WidgetModel {

    data class RatingOverview(
        val usesRating: Float?,
        val userRating: Int?,
        val isWatchList: Boolean
    ) : WidgetModel() {
        companion object
    }

    data class MovieInfoOverview(
        val genres: String,
        val length: String,
        val releaseDate: String
    ) : WidgetModel() {
        companion object
    }

    data class HeaderAndTextExpandable(
        val header: String,
        val description: String
    ) : WidgetModel() {
        companion object
    }

    data class CastAndCrewWidgetModel(
        val castHeader: String,
        val crewHeader: String,
        val castList: List<MovieCastData>,
        val crewList: List<MovieCrewData>
    ) : WidgetModel() {
        companion object
    }

    data class WhereToWatch(val providerList: List<MovieProviderData>?) : WidgetModel() {
        companion object
    }

    data class MovieLargeWidget(val movieBriefData: MovieBriefData) : WidgetModel() {
        companion object
    }

    data class Header(val header: String) : WidgetModel() {
        companion object
    }
}

class WidgetDiffUtilCallback : DiffUtil.ItemCallback<WidgetModel>() {
    override fun areItemsTheSame(oldItem: WidgetModel, newItem: WidgetModel): Boolean =
        oldItem::class == newItem::class && oldItem === newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: WidgetModel, newItem: WidgetModel): Boolean =
        oldItem == newItem
}
