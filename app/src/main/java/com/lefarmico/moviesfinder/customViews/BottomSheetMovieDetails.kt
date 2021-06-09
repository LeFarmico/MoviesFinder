package com.lefarmico.moviesfinder.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.ToggleButton
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapters.CastAdapter
import com.lefarmico.moviesfinder.adapters.SpinnerProviderAdapter
import com.lefarmico.moviesfinder.animations.MaxLineAnimator
import com.lefarmico.moviesfinder.data.appEntity.Cast
import com.lefarmico.moviesfinder.data.appEntity.MovieItem
import com.lefarmico.moviesfinder.databinding.BottomSheetMovieDetailsBinding
import com.lefarmico.moviesfinder.private.ApiConstants
import com.squareup.picasso.Picasso
import java.util.*

class BottomSheetMovieDetails(context: Context, @Nullable attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    private var binding: BottomSheetMovieDetailsBinding =
        BottomSheetMovieDetailsBinding.inflate(LayoutInflater.from(context), this, true)

    val backgroundPoster: ImageView = binding.posterBackgroundImageView
    val backButton: AppCompatButton = binding.backButton.apply { alpha = 0f }
    private val poster: ImageView = binding.posterImageView
    private val movieTitle: TextView = binding.movieTitle
    private val generalRating: RatingView = binding.movieRate
    private val movieLength: TextView = binding.length
    private val movieDescription: TextView = binding.movieDescription
    private val providerSpinner: Spinner = binding.providerSpinner
    private val releaseDate: TextView = binding.releaseDate
    private val genres: TextView = binding.genresTextView
    private val actors: RecyclerView = binding.actorsRecyclerView
    private val isWatchlist: ToggleButton = binding.favoriteToggleButton

    @SuppressLint("SetTextI18n")
    fun setMovieItem(movieItem: MovieItem) {
        genres.text = concatGenres(movieItem.genres)
        movieTitle.text = movieItem.title
        releaseDate.text = parseReleaseDate(movieItem.releaseDate)
        generalRating.setRatingValue(movieItem.rating)
        providerSpinner.adapter = SpinnerProviderAdapter(context, movieItem.watchProviders)
        isWatchlist.isChecked = movieItem.isWatchlist
        movieLength.text = "${movieItem.length}min"
        setCastAdapter(movieItem.actors)
        setPoster(movieItem.posterPath)
        setBackground(movieItem.posterPath)
        setDescription(movieItem.description)
    }

    fun watchListCallback(onChecked: () -> Unit, notChecked: () -> Unit) {
        isWatchlist.setOnClickListener {
            if (isWatchlist.isChecked) { onChecked() } else { notChecked() }
        }
    }

    fun setAlphaParamsListener(alpha: Float) {
        backButton.alpha = alpha
        backgroundPoster.alpha = alpha
    }

    private fun setBackground(posterPath: String?) {
        backgroundPoster.visibility = View.VISIBLE
        Picasso.get()
            .load(ApiConstants.IMAGES_URL + "w500" + posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(backgroundPoster)
    }

    private fun parseReleaseDate(dateStringFormat: String): String =
        dateStringFormat.split("-")[0]

    private fun concatGenres(genresList: List<String>): StringBuffer =
        StringBuffer().apply {
            genresList.forEachIndexed { index, string ->
                append(string.capitalize(Locale.ROOT))
                if (index != genresList.size - 1) { append(" / ") }
            }
        }

    private fun setPoster(posterPath: String?) {
        Picasso.get()
            .load(ApiConstants.IMAGES_URL + "w342" + posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(poster)
    }

    private fun setCastAdapter(cast: List<Cast>) {
        setAdapter(actors) {
            actors.adapter = CastAdapter().apply { setItems(cast) }
        }
    }

    private fun setAdapter(recyclerView: RecyclerView, set: (RecyclerView) -> Unit) {
        if (recyclerView.adapter == null) {
            set(recyclerView)
        }
    }

    private fun setDescription(description: String) {
        movieDescription.apply {
            text = description
            maxLines = 5
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            setOnClickListener {
                MaxLineAnimator(movieDescription, 500L, true, 5, context)
                    .start()
            }
        }
    }
}
