package com.lefarmico.moviesfinder.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.databinding.FragmentProfileBinding
import com.lefarmico.moviesfinder.viewModels.ProfileFragmentViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    val viewModel: ProfileFragmentViewModel by viewModels()
    private val TAG = this.javaClass.canonicalName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        setStatsParameters()
        viewModel.watchlistStatsLiveData.observe(viewLifecycleOwner) {
            setStatsParameters(it)
        }
        viewModel.itemsLiveData.observe(viewLifecycleOwner) { adapter ->
            adapter.setOnClickEvent { header ->
                (requireContext() as MainActivity).viewModel.onItemClick(header)
            }
            binding.watchlistRecyclerView.adapter = adapter
        }
    }

    private fun setStatsParameters(watchListCount: Int = 0, watchedCount: Int = 0) {
        binding.watchedListCount.text = watchListCount.toString()
        binding.moviesWatchedCount.text = watchedCount.toString()
    }
}
