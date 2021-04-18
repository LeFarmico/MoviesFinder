package com.lefarmico.moviesfinder.customViews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapters.GenreAdapter
import com.lefarmico.moviesfinder.adapters.ProviderAdapter
import com.lefarmico.moviesfinder.adapters.SpinnerProviderAdapter
import com.lefarmico.moviesfinder.animations.MaxLineAnimator
import com.lefarmico.moviesfinder.data.appEntity.Provider
import com.lefarmico.moviesfinder.databinding.BottomSheetMovieDetailsBinding
import com.lefarmico.moviesfinder.decorators.TopSpacingItemDecoration
import com.lefarmico.moviesfinder.private.ApiConstants
import com.squareup.picasso.Picasso

class BottomSheetMovieDetails(context: Context, @Nullable attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    private var binding: BottomSheetMovieDetailsBinding

    private val poster: ImageView
    private val movieTitle: TextView
    private val movieRate: RatingView
    private val length: TextView
    private val descriptions: TextView
    private val providerSpinner: Spinner

    private val genres: RecyclerView
    private val providers: RecyclerView
    private val actors: RecyclerView

    init {
        binding = BottomSheetMovieDetailsBinding.inflate(LayoutInflater.from(context), this, true)

        poster = binding.posterImageView
        movieTitle = binding.movieTitle
        movieRate = binding.movieRate
        length = binding.length
        descriptions = binding.movieDescription
        providerSpinner = binding.providerSpinner

        genres = binding.genreRecycler.apply {
            addItemDecoration(TopSpacingItemDecoration(2))
        }
        providers = binding.providersRecyclerView
        actors = binding.actorsRecyclerView
    }

    fun setRate(rate: Int) {
    }

    fun setProviders(providersList: List<Provider>) {
        setAdapter(providers) {
            providers.adapter = ProviderAdapter()
        }
        Log.d("provider", providers.adapter.hashCode().toString())
        (providers.adapter as ProviderAdapter).setItems(providersList)
    }

    fun setSpinnerProvider(providersList: List<Provider>) {
        val adapter = SpinnerProviderAdapter(context, providersList)
        binding.providerSpinner.adapter = adapter
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

    fun setPoster(posterPath: String) {
        Picasso.get()
            .load(ApiConstants.IMAGES_URL + "w342" + posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(poster)
    }

    private fun setAdapter(recyclerView: RecyclerView, set: (RecyclerView) -> Unit) {
        if (recyclerView.adapter == null) {
            set(recyclerView)
        }
    }
}
