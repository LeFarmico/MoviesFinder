package com.lefarmico.moviesfinder.customViews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapters.GenreAdapter
import com.lefarmico.moviesfinder.adapters.ProviderAdapter
import com.lefarmico.moviesfinder.adapters.RateMovieAdapter
import com.lefarmico.moviesfinder.animations.MaxLineAnimator
import com.lefarmico.moviesfinder.data.appEntity.MovieItem
import com.lefarmico.moviesfinder.data.appEntity.Provider
import com.lefarmico.moviesfinder.databinding.BottomSheetMovieDetailsBinding
import com.lefarmico.moviesfinder.decorators.TopSpacingItemDecoration
import com.lefarmico.moviesfinder.private.ApiConstants
import com.squareup.picasso.Picasso

class BottomSheetMovieDetailsDialogFragment(val movieItem: MovieItem) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var poster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieRate: TextView
    private lateinit var length: TextView
    private lateinit var descriptions: TextView

    private lateinit var genres: RecyclerView
    private lateinit var providers: RecyclerView
    private lateinit var yourRate: RecyclerView
    private lateinit var actors: RecyclerView

    fun instance(): BottomSheetMovieDetailsDialogFragment = BottomSheetMovieDetailsDialogFragment(movieItem)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetMovieDetailsBinding.inflate(inflater, container, false)

        poster = binding.posterImageView
        movieTitle = binding.movieTitle
        movieRate = binding.movieRate
        length = binding.length
        descriptions = binding.movieDescription

        genres = binding.genreRecycler.apply {
            addItemDecoration(TopSpacingItemDecoration(2))
        }
        providers = binding.providersRecyclerView
        yourRate = binding.rateView
        actors = binding.actorsRecyclerView

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRate(5)
        setProviders(movieItem.watchProviders)
        setGenres(movieItem.genres)
        setTitle(movieItem.title)
        setRating(movieItem.rating)
        setDescription(movieItem.description)
        setPoster(movieItem.posterPath)
    }

    fun setRate(rate: Int) {
        setAdapter(yourRate) {
            yourRate.adapter = RateMovieAdapter()
        }
        (yourRate.adapter as RateMovieAdapter).setRate(rate)
    }

    fun setProviders(providersList: List<Provider>) {
        setAdapter(providers) {
            providers.adapter = ProviderAdapter()
        }
        Log.d("provider", providers.adapter.hashCode().toString())
        (providers.adapter as ProviderAdapter).setItems(providersList)
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
        movieRate.text = requireContext().getString(R.string.rating) + rating
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
