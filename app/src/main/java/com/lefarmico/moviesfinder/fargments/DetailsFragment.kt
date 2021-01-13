
package com.lefarmico.moviesfinder.fargments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.MovieItem
import com.lefarmico.moviesfinder.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var movieItem: MovieItem
    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieDetails()

        binding.fragmentFavoritesFab.setOnClickListener {
            if (!movieItem.isFavorite) {
                binding.fragmentFavoritesFab.setImageResource(R.drawable.ic_baseline_favorite_24)
                movieItem.isFavorite = true
            } else {
                binding.fragmentFavoritesFab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                movieItem.isFavorite = false
            }
        }

        binding.fragmentShareFab.setOnClickListener {
            // создаем интент
            val intent = Intent()
            // Указываем action
            intent.action = Intent.ACTION_SEND
            // Передаем данные
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this movie: ${movieItem.title}"
            )
            // УКазываем MIME тип, чтобы система знала, какое приложения предложить
            intent.type = "text/plain"
            // Запускаем активити
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }
    private fun setMovieDetails() {
        movieItem = arguments?.get("movie") as MovieItem

        binding.fragmentDetailsPoster.setImageResource(movieItem.posterId)
        binding.fragmentDetailsToolbar.title = movieItem.title
        binding.fragmentFavoritesFab.setImageResource(
            if (movieItem.isFavorite) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )
    }
}
