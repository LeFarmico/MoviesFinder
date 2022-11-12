package com.lefarmico.moviesfinder.ui.common.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.entity.MovieDetailsModel
import com.lefarmico.moviesfinder.databinding.*
import com.lefarmico.moviesfinder.private.Private
import com.lefarmico.moviesfinder.ui.common.decorator.FirstLastPaddingItemDecorator
import com.lefarmico.moviesfinder.ui.common.decorator.PaddingItemDecoration
import com.squareup.picasso.Picasso

class MovieDetailsAdapter(
    private val onWatchListClick: (Boolean) -> Unit,
    private val onRecommendedMovieClick: (MovieBriefData) -> Unit
) : ListAdapter<MovieDetailsModel, MovieDetailsAdapter.MovieDetailsViewHolder>(WidgetDiffUtilCallback()) {

    sealed class MovieDetailsViewHolder(
        viewBinding: ViewBinding,
    ) : RecyclerView.ViewHolder(viewBinding.root)

    class HeaderViewHolder(
        private val widgetHeaderBinding: WidgetHeaderBinding
    ) : MovieDetailsViewHolder(widgetHeaderBinding) {

        val root = widgetHeaderBinding.root

        fun bind(movieDetailsModel: MovieDetailsModel.Header) {
            widgetHeaderBinding.header.text = root.context.getText(movieDetailsModel.headerRes)
        }
    }

    class RatingViewHolder(
        widgetRatingOverviewBinding: WidgetRatingOverviewBinding
    ) : MovieDetailsViewHolder(widgetRatingOverviewBinding) {

        private val userRating = widgetRatingOverviewBinding.userRate
        private val usersRating = widgetRatingOverviewBinding.movieRate
        private val watchlistToggle = widgetRatingOverviewBinding.watchlistToggle

        fun bind(ratingOverview: MovieDetailsModel.RatingOverview) {
            usersRating.setRatingValue(ratingOverview.usesRating ?: 0f)
            watchlistToggle.isChecked = ratingOverview.isWatchList
        }

        fun onCheckedWatchlistClick(onChecked: () -> Unit, notChecked: () -> Unit) {
            watchlistToggle.setOnClickListener {
                if (watchlistToggle.isChecked) { onChecked() } else { notChecked() }
            }
        }
    }

    class MovieOverviewViewHolder(
        widgetMovieOverviewBinding: WidgetMovieOverviewBinding
    ) : MovieDetailsViewHolder(widgetMovieOverviewBinding) {

        private val genres = widgetMovieOverviewBinding.genres
        private val length = widgetMovieOverviewBinding.length
        private val releaseDate = widgetMovieOverviewBinding.releaseDate

        fun bind(movieInfoOverView: MovieDetailsModel.MovieInfoOverview) {
            genres.text = movieInfoOverView.genres
            length.text = movieInfoOverView.length
            releaseDate.text = movieInfoOverView.releaseDate
        }
    }

    class HeaderAndTextViewHolder(
        widgetHeaderAndTextBinding: WidgetHeaderAndTextBinding
    ) : MovieDetailsViewHolder(widgetHeaderAndTextBinding) {

        private val header = widgetHeaderAndTextBinding.header
        private val text = widgetHeaderAndTextBinding.description
        private val root = widgetHeaderAndTextBinding.root

        fun bind(headerAndTextExpandable: MovieDetailsModel.HeaderAndTextExpandable) {
            header.text = root.context.getString(headerAndTextExpandable.header)
            text.text = headerAndTextExpandable.description
        }
    }

    class WhereToWatchViewHolder(
        widgetWhereToWatchBinding: WidgetWhereToWatchBinding
    ) : MovieDetailsViewHolder(widgetWhereToWatchBinding) {

        private val spinner = widgetWhereToWatchBinding.root
        private val binding = widgetWhereToWatchBinding

        fun bind(whereToWatch: MovieDetailsModel.WhereToWatch) {
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
    ) : MovieDetailsViewHolder(widgetMovieLargeBinding) {

        private val poster = widgetMovieLargeBinding.poster
        private val title = widgetMovieLargeBinding.title
        private val description = widgetMovieLargeBinding.description
        private val rating = widgetMovieLargeBinding.movieRate
        val root = widgetMovieLargeBinding.root

        fun bind(movieLargeWidget: MovieDetailsModel.AddedMovie) {
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
    ) : MovieDetailsViewHolder(widgetHeaderWithRecyclerBinding) {

        val recycler = widgetHeaderWithRecyclerBinding.widgetRecycler.apply {
            isNestedScrollingEnabled = false
            adapter = CastAdapter()
        }
        private val root = widgetHeaderWithRecyclerBinding.root

        fun bind(castAndCrew: MovieDetailsModel.CastAndCrew) {
            if (castAndCrew.castList.isEmpty()) {
                root.visibility = View.GONE
            } else {
                root.visibility = View.VISIBLE
                (recycler.adapter as CastAdapter)
                    .submitList(castAndCrew.castList)
                Log.d("DECORATION_COUNT", "${recycler.itemDecorationCount}")
            }
        }
    }

    class WidgetDiffUtilCallback : DiffUtil.ItemCallback<MovieDetailsModel>() {
        override fun areItemsTheSame(oldItem: MovieDetailsModel, newItem: MovieDetailsModel): Boolean =
            oldItem::class == newItem::class && oldItem === newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: MovieDetailsModel, newItem: MovieDetailsModel): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieDetailsViewHolder {
        return when (viewType) {
            MovieDetailsModel.Header.ordinal() -> HeaderViewHolder(
                WidgetHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            MovieDetailsModel.RatingOverview.ordinal() -> {
                RatingViewHolder(
                    WidgetRatingOverviewBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            MovieDetailsModel.CastAndCrew.ordinal() -> CastAndCrewViewHolder(
                WidgetHeaderWithRecyclerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            ).apply {
                val pagerSnapHelper = LinearSnapHelper()
                pagerSnapHelper.attachToRecyclerView(recycler)
                recycler.addItemDecoration(
                    PaddingItemDecoration(
                        rightPd = parent.getDimPx(R.dimen.stnd_small_margin)
                    )
                )
                recycler.addItemDecoration(
                    FirstLastPaddingItemDecorator(
                        leftPd = parent.getDimPx(R.dimen.stnd_margin)
                    )
                )
            }
            MovieDetailsModel.MovieInfoOverview.ordinal() -> MovieOverviewViewHolder(
                WidgetMovieOverviewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            MovieDetailsModel.AddedMovie.ordinal() -> MovieLargeViewHolder(
                WidgetMovieLargeBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            MovieDetailsModel.WhereToWatch.ordinal() -> WhereToWatchViewHolder(
                WidgetWhereToWatchBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            MovieDetailsModel.HeaderAndTextExpandable.ordinal() -> HeaderAndTextViewHolder(
                WidgetHeaderAndTextBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw IllegalArgumentException("Illegal view type: viewType = $viewType")
        }
    }

    private fun ViewGroup.getDimPx(@DimenRes res: Int): Int =
        this.context.resources.getDimensionPixelOffset(res)

    override fun onBindViewHolder(holder: MovieDetailsViewHolder, position: Int) {
        when (val model = getItem(position)) {
            is MovieDetailsModel.Header -> {
                holder as HeaderViewHolder
                holder.bind(model)
            }
            is MovieDetailsModel.RatingOverview -> {
                holder as RatingViewHolder
                holder.bind(model)
                holder.onCheckedWatchlistClick(
                    onChecked = { onWatchListClick(true) },
                    notChecked = { onWatchListClick(false) }
                )
            }
            is MovieDetailsModel.CastAndCrew -> {
                holder as CastAndCrewViewHolder
                holder.bind(model)
            }
            is MovieDetailsModel.HeaderAndTextExpandable -> {
                holder as HeaderAndTextViewHolder
                holder.bind(model)
            }
            is MovieDetailsModel.MovieInfoOverview -> {
                holder as MovieOverviewViewHolder
                holder.bind(model)
            }
            is MovieDetailsModel.AddedMovie -> {
                holder as MovieLargeViewHolder
                holder.bind(model)
                // TODO debug
                holder.root.setOnClickListener {
                    onRecommendedMovieClick(model.movieBriefData)
                }
            }
            is MovieDetailsModel.WhereToWatch -> {
                holder as WhereToWatchViewHolder
                holder.bind(model)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).ordinal()

    private inline fun <reified T : Any> T.ordinal(): Int {
        if (T::class.isSealed) {
            return T::class.java.classes.indexOfFirst { sub -> sub == javaClass }
        }
        val klass = if (T::class.isCompanion) {
            javaClass.declaringClass
        } else {
            javaClass
        }
        return klass.superclass?.classes?.indexOfFirst { it == klass } ?: -1
    }
}
