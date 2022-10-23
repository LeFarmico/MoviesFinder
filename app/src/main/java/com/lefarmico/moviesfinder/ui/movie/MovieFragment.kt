package com.lefarmico.moviesfinder.ui.movie

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.data.entity.MovieDetailsAdapterModel
import com.lefarmico.moviesfinder.databinding.FragmentMovieBinding
import com.lefarmico.moviesfinder.ui.base.BaseFragment
import com.lefarmico.moviesfinder.ui.common.adapter.MovieDetailsAdapter
import com.lefarmico.moviesfinder.ui.navigation.api.Router
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination
import com.lefarmico.moviesfinder.ui.navigation.api.params.MovieFragmentParams
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : BaseFragment<MovieViewModel, FragmentMovieBinding>() {

    @Inject lateinit var router: Router

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val movieParams = requireArguments().getParcelable<MovieFragmentParams>(BUNDLE_KEY)
            viewModel.launchMovieDetailed(movieParams!!.movieId)
        } catch (e: NullPointerException) {
            closeFragment()
        }

        movieDetailsAdapter = MovieDetailsAdapter(
            onWatchListClick = { isChecked ->
                if (isChecked) {
                    viewModel.saveMovieToWatchlist()
                } else {
                    viewModel.removeMovieFromWatchlist()
                }
            },
            onRecommendedMovieClick = {
                router.navigate(
                    ScreenDestination.MovieToSelfDestination,
                    MovieFragmentParams(it.movieId)
                )
                // TODO debug
//                requireActivity().supportFragmentManager.beginTransaction()
//                    .addToBackStack("MovieFragment${requireActivity().supportFragmentManager.backStackEntryCount}")
//                    .add(
//                        R.id.nav_host_fragment,
//                        MovieFragment::class.java,
//                        createBundle()
//                    ).commit()
            }
        )
        setUp()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.apply {
                movieData?.let {
                    launchItemDetails(it, movieDetailsAdapterModelList)
                }
                toast?.let { message ->
                    showToast(message)
                }
                val bottomSheetState = when (bottomSheetState) {
                    MovieFragmentState.BottomSheetState.Expanded -> BottomSheetBehavior.STATE_EXPANDED
                    MovieFragmentState.BottomSheetState.HalfExpanded -> BottomSheetBehavior.STATE_HALF_EXPANDED
                }
                binding.bottomSheet.getBehavior().state = bottomSheetState
            }
        }
    }

    private fun launchItemDetails(movieDetailedData: MovieDetailedData, movieDetailsAdapterModelList: List<MovieDetailsAdapterModel>) {
        movieDetailsAdapter.submitList(movieDetailsAdapterModelList)
        binding.bottomSheet.setMovieItem(movieDetailedData)
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
                        closeFragment()
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
                    closeFragment()
                }
            }
        }
        lifecycle.addObserver(binding.bottomSheet)
    }

    private fun closeFragment() {
        router.back()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show().also {
            viewModel.cleanToast()
        }
    }

    companion object {
        private const val BUNDLE_KEY = "movie_fragment"
        fun createBundle(data: Parcelable): Bundle {
            if (data !is MovieFragmentParams) {
                throw IllegalArgumentException("MovieFragment.createBundle() accepts MovieFragmentParams as the type")
            }
            return Bundle().apply {
                putParcelable(BUNDLE_KEY, data)
            }
        }
    }
}
