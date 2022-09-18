package com.lefarmico.moviesfinder.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lefarmico.moviesfinder.databinding.BottomSheetMovieBinding
import com.lefarmico.moviesfinder.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel, BottomSheetMovieBinding>() {

    private val TAG = this.javaClass.canonicalName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        binding.toolbar.title = "Dragon Ball Super: Super Hero"
//        setStatsParameters()
        viewModel.watchlistStatsLiveData.observe(viewLifecycleOwner) {
//            setStatsParameters(it)
        }
        viewModel.itemsLiveData.observe(viewLifecycleOwner) { _ ->
//            adapter.setOnClickEvent { header ->
//                (requireContext() as MainActivity).viewModel.onItemClick(header)
//            }
//            binding.watchlistRecyclerView.adapter = adapter
        }
    }

    override fun getInjectViewModel(): ProfileViewModel {
        val viewModel: ProfileViewModel by viewModels()
        return viewModel
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): BottomSheetMovieBinding = BottomSheetMovieBinding.inflate(inflater, container, false)

//    private fun setStatsParameters(watchListCount: Int = 0, watchedCount: Int = 0) {
//        binding.watchedListCount.text = watchListCount.toString()
//        binding.moviesWatchedCount.text = watchedCount.toString()
//    }
}
