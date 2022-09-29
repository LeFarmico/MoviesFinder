package com.lefarmico.moviesfinder.ui.main.adapter

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
import com.lefarmico.moviesfinder.ui.main.adapter.model.MovieDetailsModel
import com.lefarmico.moviesfinder.ui.main.adapter.model.WidgetDiffUtilCallback
import com.squareup.picasso.Picasso

class MovieDetailsAdapter : ListAdapter<MovieDetailsModel, MovieDetailsAdapter.MovieDetailsViewHolder>(WidgetDiffUtilCallback()) {

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

        fun onCheckedWatchlist(onChecked: () -> Unit, notChecked: () -> Unit) {
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
//            movieInfoOverView.genreList.reduce { acc, s -> "$acc / $s" }
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

        fun bind(movieLargeWidget: MovieDetailsModel.MovieLargeModel) {
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

        private val header = widgetHeaderWithRecyclerBinding.header
        private val recycler = widgetHeaderWithRecyclerBinding.castRecycler
        private val root = widgetHeaderWithRecyclerBinding.root

        fun bind(castAndCrewMovieDetailsModel: MovieDetailsModel.CastAndCrewMovieDetailsModel) {
            if (castAndCrewMovieDetailsModel.castList.isEmpty()) {
                root.visibility = View.GONE
            } else {

                header.text = root.context.getString(castAndCrewMovieDetailsModel.castHeader)
                root.visibility = View.VISIBLE
                recycler.adapter = CastAdapter().apply {
                    submitList(castAndCrewMovieDetailsModel.castList)
                }
            }
        }
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
            MovieDetailsModel.CastAndCrewMovieDetailsModel.ordinal() -> CastAndCrewViewHolder(
                WidgetHeaderWithRecyclerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            MovieDetailsModel.MovieInfoOverview.ordinal() -> MovieOverviewViewHolder(
                WidgetMovieOverviewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            MovieDetailsModel.MovieLargeModel.ordinal() -> MovieLargeViewHolder(
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

    override fun onBindViewHolder(holder: MovieDetailsViewHolder, position: Int) {
        when (val model = getItem(position)) {
            is MovieDetailsModel.Header -> {
                holder as HeaderViewHolder
                holder.bind(model)
            }
            is MovieDetailsModel.RatingOverview -> {
                holder as RatingViewHolder
                holder.bind(model)
                holder.onCheckedWatchlist(
                    onChecked = {},
                    notChecked = {}
                )
            }
            is MovieDetailsModel.CastAndCrewMovieDetailsModel -> {
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
            is MovieDetailsModel.MovieLargeModel -> {
                holder as MovieLargeViewHolder
                holder.bind(model)
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
