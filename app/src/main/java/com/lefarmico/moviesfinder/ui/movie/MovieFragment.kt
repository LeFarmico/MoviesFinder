package com.lefarmico.moviesfinder.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lefarmico.moviesfinder.databinding.FragmentMovieBinding
import com.lefarmico.moviesfinder.ui.base.BaseFragment
import com.lefarmico.moviesfinder.ui.main.adapter.MovieDetailsAdapter

class MovieFragment : BaseFragment<MovieViewModel, FragmentMovieBinding>() {

    private lateinit var movieDetailsAdapter: MovieDetailsAdapter

    override fun getInjectViewModel(): MovieViewModel {
        val viewModel: MovieViewModel by viewModels()
        return viewModel
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMovieBinding {
        return FragmentMovieBinding.inflate(
            LayoutInflater.from(context), container, false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            val movieId = savedInstanceState.getInt(BUNDLE_KEY)
            viewModel.launchMovieDetailed(movieId)
        }

        movieDetailsAdapter = MovieDetailsAdapter { isChecked ->
            if (isChecked) {
                viewModel.saveMovieToWatchlist()
            } else {
                viewModel.removeMovieFromWatchlist()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        binding.bottomSheet.bindBottomSheetBehaviour()
        binding.bottomSheet.setRecyclerAdapter(movieDetailsAdapter)
        binding.bottomSheet.apply {

            onSlide = { slideOffset ->
                binding.blackBackgroundFrameLayout.alpha = slideOffset
            }
            onHidden = {
                //
                binding.apply {
                    blackBackgroundFrameLayout.isClickable = false
                    disableScroll()
                    enableDragging()
                }
            }
            onHalfExpanded = { behavior ->
                binding.apply {
                    blackBackgroundFrameLayout.isClickable = true
                    blackBackgroundFrameLayout.setOnClickListener { view ->
                        view.isClickable = false
                        behavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                }
                disableScroll()
            }
            onExpanded = {
                viewModel.setBottomSheetState(MovieFragmentState.BottomSheetState.Expanded)
                disableDragging()
                enableScroll()
                onNavigateUpPressed {
                    it.state = BottomSheetBehavior.STATE_HIDDEN
                    removeFragment()
                }
            }
        }
        lifecycle.addObserver(binding.bottomSheet)
    }

    private fun removeFragment() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }

    companion object {
        private const val BUNDLE_KEY = "movie_fragment"

        fun createBundle(data: Int): Bundle {
            return Bundle().apply {
                putInt(BUNDLE_KEY, data)
            }
        }
    }
}
