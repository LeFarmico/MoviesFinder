
package com.lefarmico.moviesfinder.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.FragmentDetailsBinding
import com.lefarmico.moviesfinder.models.Item
import com.lefarmico.moviesfinder.models.MovieItemModel
import com.lefarmico.moviesfinder.private.PrivateData
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {
    private lateinit var movieItem: Item
    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieDetails()

        binding.fragmentFavoritesFab.setOnClickListener {
            if (!movieItem.isFavorite) {
                Toast.makeText(requireContext(), "add to favorite", Toast.LENGTH_SHORT).show()
                binding.fragmentFavoritesFab.setImageResource(R.drawable.ic_baseline_favorite_24)
                movieItem.isFavorite = true
            } else {
                Toast.makeText(requireContext(), "remove from favorite", Toast.LENGTH_SHORT).show()
                binding.fragmentFavoritesFab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                movieItem.isFavorite = false
            }
        }

        binding.fragmentShareFab.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this movie: ${movieItem.title}"
            )
            // УКазываем MIME тип, чтобы система знала, какое приложения предложить
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }
    private fun setMovieDetails() {
        movieItem = arguments?.get("movie") as MovieItemModel
        binding.item = movieItem
        Picasso.get()
            .load(PrivateData.ApiConstants.IMAGES_URL + "w780" + movieItem.posterPath)
            .centerCrop()
            .into(binding.fragmentDetailsPoster)
    }
}
