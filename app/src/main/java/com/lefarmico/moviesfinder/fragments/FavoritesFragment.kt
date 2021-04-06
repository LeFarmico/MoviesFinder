package com.lefarmico.moviesfinder.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.adapters.ItemAdapter
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.databinding.FragmentFavoritesBinding
import com.lefarmico.moviesfinder.models.ItemHeaderModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var mainRepository: MainRepository
    private val TAG = this.javaClass.canonicalName

    var cachedItems = listOf<ItemHeaderModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

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

        val adapter = ItemAdapter {
            Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        }
        binding.showSevedMovies.setOnClickListener {
            showSavedMovies(adapter)
        }

        binding.favoritesRecycler.apply {
            this.adapter = adapter
        }

        GlobalScope.launch {
            cachedItems = mainRepository.getAllFromDB()
        }
    }

    private fun showSavedMovies(adapter: ItemAdapter) {
        adapter.updateItems(cachedItems)
    }
}
