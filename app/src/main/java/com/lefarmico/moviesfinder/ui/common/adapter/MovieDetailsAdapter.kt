package com.lefarmico.moviesfinder.ui.common.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
) : ListAdapter<MovieDetailsModel, MovieDetailsAdapter.DefaultViewHolder<*>>(WidgetDiffUtilCallback()) {

    sealed class DefaultViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(model: T)
    }

    class HeaderViewHolder(view: View) : DefaultViewHolder<MovieDetailsModel.Header>(view) {

        private val binding = WidgetHeaderBinding.bind(view)
        private val header = binding.header

        override fun bind(model: MovieDetailsModel.Header) {
            header.text = header.context.getText(model.headerRes)
        }
    }

    class RatingViewHolder(
        view: View,
        private val onChecked: () -> Unit,
        private val notChecked: () -> Unit
    ) : DefaultViewHolder<MovieDetailsModel.RatingOverview>(view) {

        private val binding = WidgetRatingOverviewBinding.bind(view)
        private val userRating = binding.userRate
        private val usersRating = binding.movieRate
        private val watchlistToggle = binding.watchlistToggle

        override fun bind(model: MovieDetailsModel.RatingOverview) {
            usersRating.setRatingValue(model.usesRating ?: 0f)
            watchlistToggle.isChecked = model.isWatchList

            watchlistToggle.setOnClickListener {
                if (watchlistToggle.isChecked) { onChecked() } else { notChecked() }
            }
        }
    }

    class MovieOverviewViewHolder(view: View) :
        DefaultViewHolder<MovieDetailsModel.MovieInfoOverview>(view) {

        private val binding = WidgetMovieOverviewBinding.bind(view)
        private val genres = binding.genres
        private val length = binding.length
        private val releaseDate = binding.releaseDate

        override fun bind(model: MovieDetailsModel.MovieInfoOverview) {
            genres.text = model.genres
            length.text = model.length
            releaseDate.text = model.releaseDate
        }
    }

    class HeaderAndTextViewHolder(view: View) :
        DefaultViewHolder<MovieDetailsModel.HeaderAndTextExpandable>(view) {

        private val binding = WidgetHeaderAndTextBinding.bind(view)
        private val header = binding.header
        private val text = binding.description
        private val root = binding.root

        override fun bind(model: MovieDetailsModel.HeaderAndTextExpandable) {
            header.text = root.context.getString(model.header)
            text.text = model.description
        }
    }

    class WhereToWatchViewHolder(view: View) :
        DefaultViewHolder<MovieDetailsModel.WhereToWatch>(view) {

        private val binding = WidgetWhereToWatchBinding.bind(view)
        private val spinner = binding.root

        override fun bind(model: MovieDetailsModel.WhereToWatch) {
            if (model.providerList == null || model.providerList.isEmpty()) {
                spinner.visibility = View.GONE
            } else {
                spinner.visibility = View.VISIBLE
                spinner.adapter = SpinnerProviderAdapter(binding.root.context, model.providerList)
            }
        }
    }

    class AddedMovieViewHolder(
        view: View,
        private val onClick: (Int) -> Unit
    ) : DefaultViewHolder<MovieDetailsModel.AddedMovie>(view) {

        private val binding = WidgetMovieLargeBinding.bind(view)
        private val poster = binding.poster
        private val title = binding.title
        private val description = binding.description
        private val rating = binding.movieRate

        override fun bind(model: MovieDetailsModel.AddedMovie) {
            Picasso.get()
                .load(Private.IMAGES_URL + "w342" + model.movieBriefData.posterPath)
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(poster)
            title.text = model.movieBriefData.title
            description.text = model.movieBriefData.description
            if (model.movieBriefData.rating != null) {
                rating.setRatingValue(model.movieBriefData.rating)
                rating.visibility = View.VISIBLE
            } else {
                rating.visibility = View.GONE
            }
            binding.root.setOnClickListener { onClick(absoluteAdapterPosition) }
        }
    }

    class CastAndCrewViewHolder(view: View) :
        DefaultViewHolder<MovieDetailsModel.CastAndCrew>(view) {

        private val binding = WidgetHeaderWithRecyclerBinding.bind(view)
        private val recycler = binding.widgetRecycler.apply {
            isNestedScrollingEnabled = false
            adapter = CastAdapter()
            addItemDecoration(
                PaddingItemDecoration(
                    rightPd = view.getDimPx(R.dimen.stnd_small_margin)
                )
            )
            addItemDecoration(
                FirstLastPaddingItemDecorator(
                    leftPd = view.getDimPx(R.dimen.stnd_margin)
                )
            )
        }

        override fun bind(model: MovieDetailsModel.CastAndCrew) {
            if (model.castList.isEmpty()) {
                binding.root.visibility = View.GONE
            } else {
                binding.root.visibility = View.VISIBLE
                (recycler.adapter as CastAdapter)
                    .submitList(model.castList)
                Log.d("DECORATION_COUNT", "${recycler.itemDecorationCount}")
            }
        }

        private fun View.getDimPx(@DimenRes res: Int): Int =
            this.context.resources.getDimensionPixelOffset(res)
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
    ): DefaultViewHolder<*> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.widget_header_with_recycler -> CastAndCrewViewHolder(view)
            R.layout.widget_header -> HeaderViewHolder(view)
            R.layout.widget_header_and_text -> HeaderAndTextViewHolder(view)
            R.layout.widget_movie_overview -> MovieOverviewViewHolder(view)
            R.layout.widget_where_to_watch -> WhereToWatchViewHolder(view)
            R.layout.widget_movie_large -> AddedMovieViewHolder(
                view,
                onClick = { position ->
                    val item = getItem(position) as MovieDetailsModel.AddedMovie
                    onRecommendedMovieClick(item.movieBriefData)
                }
            )
            R.layout.widget_rating_overview -> RatingViewHolder(
                view,
                onChecked = { onWatchListClick(true) },
                notChecked = { onWatchListClick(false) }
            )
            else -> throw IllegalArgumentException("Illegal view type: viewType = $viewType")
        }
    }

    override fun onBindViewHolder(holder: DefaultViewHolder<*>, position: Int) {
        val item = getItem(position)
        when (holder) {
            is AddedMovieViewHolder -> holder.bind(item as MovieDetailsModel.AddedMovie)
            is CastAndCrewViewHolder -> holder.bind(item as MovieDetailsModel.CastAndCrew)
            is HeaderAndTextViewHolder -> holder.bind(item as MovieDetailsModel.HeaderAndTextExpandable)
            is HeaderViewHolder -> holder.bind(item as MovieDetailsModel.Header)
            is MovieOverviewViewHolder -> holder.bind(item as MovieDetailsModel.MovieInfoOverview)
            is RatingViewHolder -> holder.bind(item as MovieDetailsModel.RatingOverview)
            is WhereToWatchViewHolder -> holder.bind(item as MovieDetailsModel.WhereToWatch)
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is MovieDetailsModel.AddedMovie -> R.layout.widget_movie_large
        is MovieDetailsModel.CastAndCrew -> R.layout.widget_header_with_recycler
        is MovieDetailsModel.Header -> R.layout.widget_header
        is MovieDetailsModel.HeaderAndTextExpandable -> R.layout.widget_header_and_text
        is MovieDetailsModel.MovieInfoOverview -> R.layout.widget_movie_overview
        is MovieDetailsModel.RatingOverview -> R.layout.widget_rating_overview
        is MovieDetailsModel.WhereToWatch -> R.layout.widget_where_to_watch
    }
}
