package com.lefarmico.moviesfinder.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lefarmico.moviesfinder.databinding.FragmentProfileBinding
import com.lefarmico.moviesfinder.ui.common.adapter.WatchListAdapter
import com.lefarmico.moviesfinder.utils.mapper.toBriefData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var _binding: FragmentProfileBinding
    private val binding get() = _binding

    val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWatchlistMovies()

        val adapter = WatchListAdapter {}
        binding.watchlistRecyclerView.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner) { movieDetailedList ->
            adapter.setItems(movieDetailedList.map { it.toBriefData() })
        }
    }
//    private fun setStatsParameters(watchListCount: Int = 0, watchedCount: Int = 0) {
//        binding.watchedListCount.text = watchListCount.toString()
//        binding.moviesWatchedCount.text = watchedCount.toString()
//    }
}
