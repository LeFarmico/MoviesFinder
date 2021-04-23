package com.lefarmico.moviesfinder.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapters.GenreAdapter
import com.lefarmico.moviesfinder.adapters.SpinnerProviderAdapter
import com.lefarmico.moviesfinder.animations.MaxLineAnimator
import com.lefarmico.moviesfinder.data.appEntity.Provider
import com.lefarmico.moviesfinder.databinding.BottomSheetMovieDetailsBinding
import com.lefarmico.moviesfinder.decorators.TopSpacingItemDecoration
import com.lefarmico.moviesfinder.private.ApiConstants
import com.squareup.picasso.Picasso

class BottomSheetMovieDetails(context: Context, @Nullable attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    private var binding: BottomSheetMovieDetailsBinding =
        BottomSheetMovieDetailsBinding.inflate(LayoutInflater.from(context), this, true)

    private val poster: ImageView = binding.posterImageView
    val backgroundPoster: ImageView = binding.posterBackgroundImageView
    private val movieTitle: TextView = binding.movieTitle
    private val movieRate: RatingView = binding.movieRate
    private val length: TextView = binding.length
    private val descriptions: TextView = binding.movieDescription
    private val providerSpinner: Spinner = binding.providerSpinner

    private val genres: RecyclerView = binding.genreRecycler.apply {
        addItemDecoration(TopSpacingItemDecoration(2))
    }
    private val actors: RecyclerView = binding.actorsRecyclerView

    lateinit var posterPath: String

    fun setRate(rate: Int) {
    }

    fun setLength() {
    }

    fun setBackground(posterPath: String?) {
        backgroundPoster.visibility = View.VISIBLE
        Picasso.get()
            .load(ApiConstants.IMAGES_URL + "w500" + posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(backgroundPoster)
    }

    fun setReleaseDate(dateStringFormat: String) {
        val date = dateStringFormat.split("-")
        binding.releaseDate.text = date[0]
    }

    fun setSpinnerProvider(providersList: List<Provider>) {
        val adapter = SpinnerProviderAdapter(context, providersList)
        providerSpinner.adapter = adapter
    }

    fun setGenres(genresList: List<String>) {
        setAdapter(genres) {
            genres.adapter = GenreAdapter()
        }
        (genres.adapter as GenreAdapter).setItems(genresList)
    }

    fun setTitle(title: String) {
        movieTitle.text = title
    }

    fun setRating(rating: Double) {
        movieRate.setRatingValue(rating)
    }

    fun setDescription(description: String) {
        descriptions.apply {
            text = description
            maxLines = 5
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            setOnClickListener {
                MaxLineAnimator(descriptions, 500L, true, 5, context)
                    .start()
            }
        }
    }

    fun setPoster(posterPath: String?) {
        Picasso.get()
            .load(ApiConstants.IMAGES_URL + "w342" + posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(poster)
        if (posterPath != null)
            this.posterPath = posterPath
        else
            this.posterPath = ""
    }

    private fun setAdapter(recyclerView: RecyclerView, set: (RecyclerView) -> Unit) {
        if (recyclerView.adapter == null) {
            set(recyclerView)
        }
    }
}
