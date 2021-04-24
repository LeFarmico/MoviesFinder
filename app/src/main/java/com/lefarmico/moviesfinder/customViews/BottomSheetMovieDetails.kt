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
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapters.CastAdapter
import com.lefarmico.moviesfinder.adapters.SpinnerProviderAdapter
import com.lefarmico.moviesfinder.animations.MaxLineAnimator
import com.lefarmico.moviesfinder.data.appEntity.Cast
import com.lefarmico.moviesfinder.data.appEntity.Provider
import com.lefarmico.moviesfinder.databinding.BottomSheetMovieDetailsBinding
import com.lefarmico.moviesfinder.private.ApiConstants
import com.squareup.picasso.Picasso
import java.util.*

class BottomSheetMovieDetails(context: Context, @Nullable attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    private var binding: BottomSheetMovieDetailsBinding =
        BottomSheetMovieDetailsBinding.inflate(LayoutInflater.from(context), this, true)

    val backgroundPoster: ImageView = binding.posterBackgroundImageView
    val backButton: AppCompatButton = binding.backButton.apply {
        alpha = 0f
    }
    private val poster: ImageView = binding.posterImageView
    private val movieTitle: TextView = binding.movieTitle
    private val movieRate: RatingView = binding.movieRate
    private val length: TextView = binding.length
    private val descriptions: TextView = binding.movieDescription
    private val providerSpinner: Spinner = binding.providerSpinner

    private val genres: TextView = binding.genresTextView
    private val actors: RecyclerView = binding.actorsRecyclerView

    lateinit var posterPath: String

    fun setRate(rate: Int) {
    }

    fun setLength(movieLength: Int) {
        length.text = "$movieLength m"
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
        var capitalizeGenres = ""
        for (i in genresList.indices) {
            capitalizeGenres += if (i + 1 == genresList.size) {
                genresList[i].capitalize(Locale.ROOT)
            } else {
                genresList[i].capitalize(Locale.ROOT) + " / "
            }
        }
        genres.text = capitalizeGenres
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

    fun setActors(cast: List<Cast>) {
        setAdapter(actors) {
            actors.adapter = CastAdapter()
        }
        (actors.adapter as CastAdapter).setItems(cast)
    }

    private fun setAdapter(recyclerView: RecyclerView, set: (RecyclerView) -> Unit) {
        if (recyclerView.adapter == null) {
            set(recyclerView)
        }
    }
}
