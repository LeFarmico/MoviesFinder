package com.lefarmico.moviesfinder.ui.common.adapter.widgetAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.*
import com.lefarmico.moviesfinder.private.Private
import com.lefarmico.moviesfinder.ui.common.adapter.CastAdapter
import com.lefarmico.moviesfinder.ui.common.adapter.SpinnerProviderAdapter
import com.lefarmico.moviesfinder.ui.common.adapter.widgetAdapter.widget.WidgetDiffUtilCallback
import com.lefarmico.moviesfinder.ui.common.adapter.widgetAdapter.widget.WidgetModel
import com.squareup.picasso.Picasso

class WidgetAdapter : ListAdapter<WidgetModel, WidgetAdapter.WidgetViewHolder>(WidgetDiffUtilCallback()) {

    sealed class WidgetViewHolder(
        viewBinding: ViewBinding,
    ) : RecyclerView.ViewHolder(viewBinding.root)

    class WidgetHeaderViewHolder(
        private val widgetHeaderBinding: WidgetHeaderBinding
    ) : WidgetViewHolder(widgetHeaderBinding) {
        fun bind(widgetModel: WidgetModel.Header) {
            widgetHeaderBinding.header.text = widgetModel.header
        }
    }

    class RatingViewHolder(
        widgetRatingOverviewBinding: WidgetRatingOverviewBinding
    ) : WidgetViewHolder(widgetRatingOverviewBinding) {

        private val userRating = widgetRatingOverviewBinding.userRate
        private val usersRating = widgetRatingOverviewBinding.movieRate
        private val watchlistToggle = widgetRatingOverviewBinding.watchlistToggle

        fun bind(ratingOverview: WidgetModel.RatingOverview) {
            usersRating.setRatingValue(ratingOverview.usesRating ?: 0f)
            watchlistToggle.isChecked = ratingOverview.isWatchList
        }

        fun onCheckedWatchlist(onChecked: () -> Unit, notChecked: () -> Unit) {
            watchlistToggle.setOnClickListener {
                if (watchlistToggle.isChecked) { onChecked() } else { notChecked() }
            }
        }
    }

    class MovieOverviewViewHolder(
        widgetMovieOverviewBinding: WidgetMovieOverviewBinding
    ) : WidgetViewHolder(widgetMovieOverviewBinding) {

        private val genres = widgetMovieOverviewBinding.genres
        private val length = widgetMovieOverviewBinding.length
        private val releaseDate = widgetMovieOverviewBinding.releaseDate

        fun bind(movieInfoOverView: WidgetModel.MovieInfoOverview) {
//            movieInfoOverView.genreList.reduce { acc, s -> "$acc / $s" }
            genres.text = movieInfoOverView.genres
            length.text = movieInfoOverView.length
            releaseDate.text = movieInfoOverView.releaseDate
        }
    }

    class HeaderAndTextViewHolder(
        widgetHeaderAndTextBinding: WidgetHeaderAndTextBinding
    ) : WidgetViewHolder(widgetHeaderAndTextBinding) {

        private val header = widgetHeaderAndTextBinding.header
        private val text = widgetHeaderAndTextBinding.description

        fun bind(headerAndTextExpandable: WidgetModel.HeaderAndTextExpandable) {
            header.text = headerAndTextExpandable.header
            text.text = headerAndTextExpandable.description
        }
    }

    class WhereToWatchViewHolder(
        widgetWhereToWatchBinding: WidgetWhereToWatchBinding
    ) : WidgetViewHolder(widgetWhereToWatchBinding) {

        private val spinner = widgetWhereToWatchBinding.root
        private val binding = widgetWhereToWatchBinding

        fun bind(whereToWatch: WidgetModel.WhereToWatch) {
            if (whereToWatch.providerList == null || whereToWatch.providerList.isEmpty()) {
                spinner.visibility = View.GONE
            } else {
                spinner.visibility = View.VISIBLE
                spinner.adapter = SpinnerProviderAdapter(binding.root.context, whereToWatch.providerList)
            }
        }
    }

    class MovieLargeViewHolder(
        widgetMovieLargeBinding: WidgetMovieLargeBinding
    ) : WidgetViewHolder(widgetMovieLargeBinding) {

        private val poster = widgetMovieLargeBinding.poster
        private val title = widgetMovieLargeBinding.title
        private val description = widgetMovieLargeBinding.description
        private val rating = widgetMovieLargeBinding.movieRate

        fun bind(movieLargeWidget: WidgetModel.MovieLargeWidget) {
            Picasso.get()
                .load(Private.IMAGES_URL + "w342" + movieLargeWidget.movieBriefData.posterPath)
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(poster)
            title.text = movieLargeWidget.movieBriefData.title
            description.text = movieLargeWidget.movieBriefData.description
            if (movieLargeWidget.movieBriefData.rating != null) {
                rating.setRatingValue(movieLargeWidget.movieBriefData.rating)
                rating.visibility = View.VISIBLE
            } else {
                rating.visibility = View.GONE
            }
        }
    }

    class CastAndCrewViewHolder(
        widgetHeaderWithRecyclerBinding: WidgetHeaderWithRecyclerBinding
    ) : WidgetViewHolder(widgetHeaderWithRecyclerBinding) {

        private val header = widgetHeaderWithRecyclerBinding.header
        private val recycler = widgetHeaderWithRecyclerBinding.castRecycler
        private val root = widgetHeaderWithRecyclerBinding.root

        fun bind(castAndCrewWidgetModel: WidgetModel.CastAndCrewWidgetModel) {
            if (castAndCrewWidgetModel.castList.isEmpty()) {
                root.visibility = View.GONE
            } else {
                header.text = castAndCrewWidgetModel.castHeader
                root.visibility = View.VISIBLE
                recycler.adapter = CastAdapter().apply {
                    submitList(castAndCrewWidgetModel.castList)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WidgetViewHolder {
        return when (viewType) {
            WidgetModel.Header.ordinal() -> WidgetHeaderViewHolder(
                WidgetHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            WidgetModel.RatingOverview.ordinal() -> {
                RatingViewHolder(
                    WidgetRatingOverviewBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            WidgetModel.CastAndCrewWidgetModel.ordinal() -> CastAndCrewViewHolder(
                WidgetHeaderWithRecyclerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            WidgetModel.MovieInfoOverview.ordinal() -> MovieOverviewViewHolder(
                WidgetMovieOverviewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            WidgetModel.MovieLargeWidget.ordinal() -> MovieLargeViewHolder(
                WidgetMovieLargeBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            WidgetModel.WhereToWatch.ordinal() -> WhereToWatchViewHolder(
                WidgetWhereToWatchBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            WidgetModel.HeaderAndTextExpandable.ordinal() -> HeaderAndTextViewHolder(
                WidgetHeaderAndTextBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw IllegalArgumentException("Illegal view type: viewType = $viewType")
        }
    }

    override fun onBindViewHolder(holder: WidgetViewHolder, position: Int) {
        when (val model = getItem(position)) {
            is WidgetModel.Header -> {
                holder as WidgetHeaderViewHolder
                holder.bind(model)
            }
            is WidgetModel.RatingOverview -> {
                holder as RatingViewHolder
                holder.bind(model)
                holder.onCheckedWatchlist(
                    onChecked = {},
                    notChecked = {}
                )
            }
            is WidgetModel.CastAndCrewWidgetModel -> {
                holder as CastAndCrewViewHolder
                holder.bind(model)
            }
            is WidgetModel.HeaderAndTextExpandable -> {
                holder as HeaderAndTextViewHolder
                holder.bind(model)
            }
            is WidgetModel.MovieInfoOverview -> {
                holder as MovieOverviewViewHolder
                holder.bind(model)
            }
            is WidgetModel.MovieLargeWidget -> {
                holder as MovieLargeViewHolder
                holder.bind(model)
            }
            is WidgetModel.WhereToWatch -> {
                holder as WhereToWatchViewHolder
                holder.bind(model)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).ordinal()

    private inline fun <reified T : Any> T.ordinal(): Int {
        if (T::class.isSealed) {
            return T::class.java.classes.indexOfFirst { sub -> sub == javaClass }.also {
                Log.d("ORDINAL", "${T::class} viewType = $it")
            }
        }
        val klass = if (T::class.isCompanion) {
            javaClass.declaringClass
        } else {
            javaClass
        }
        return klass.superclass?.classes?.indexOfFirst { it == klass }.also {
            Log.d("ORDINAL", "${T::class} viewType = $it")
        } ?: -1
    }
}
