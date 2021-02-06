package com.lefarmico.moviesfinder.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.adapters.ItemAdapter
import com.lefarmico.moviesfinder.models.MovieItemModel
import com.lefarmico.moviesfinder.databinding.FragmentFavoritesBinding
import com.lefarmico.moviesfinder.decorators.TopSpacingItemDecoration

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val TAG = this.javaClass.canonicalName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        val favoritesList: List<MovieItemModel> = emptyList()

        binding.favoritesRecycler.apply {
            adapter = ItemAdapter {
                val intent = Intent(requireContext(), DetailsFragment::class.java)
                intent.putExtra("movie", it)
                (requireActivity() as MainActivity).launchDetailsFragment(it)
                val decorator = TopSpacingItemDecoration(8)
                addItemDecoration(decorator)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}
