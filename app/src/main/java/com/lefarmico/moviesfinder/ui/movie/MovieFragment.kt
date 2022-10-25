package com.lefarmico.moviesfinder.ui.movie

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lefarmico.moviesfinder.databinding.FragmentMovieBinding
import com.lefarmico.moviesfinder.ui.base.BaseFragment
import com.lefarmico.moviesfinder.ui.common.OnBackPressListener
import com.lefarmico.moviesfinder.ui.common.adapter.MovieDetailsAdapter
import com.lefarmico.moviesfinder.ui.navigation.api.NotificationType
import com.lefarmico.moviesfinder.ui.navigation.api.Router
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination
import com.lefarmico.moviesfinder.ui.navigation.api.params.MovieFragmentParams
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment :
    BaseFragment<MovieViewModel, FragmentMovieBinding>(),
    OnBackPressListener,
    BottomSheetStateListener,
    BottomSheetBehaviourHandler by BottomSheetBehaviourHandlerImpl() {

    @Inject lateinit var router: Router

    private var isScrollEnabled = true
    private var isDraggingEnabled = true

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

        bindBS(binding.bottomSheet)
        bindBSStateListener(this)

        // AppBar layout behaviour
        val layoutParams = binding.bottomSheet.appBar.layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            layoutParams.apply {
                if (behavior == null)
                    behavior = AppBarLayout.Behavior()
                val behavior = behavior as AppBarLayout.Behavior
                behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                    override fun canDrag(appBarLayout: AppBarLayout): Boolean = isScrollEnabled
                })
            }
        }

        // BottomSheet layout behaviour
        if (savedInstanceState != null) {
            when (savedInstanceState.getInt(SHEET_STATE)) {
                BottomSheetBehavior.STATE_HALF_EXPANDED -> halfExpandBS()
                BottomSheetBehavior.STATE_EXPANDED -> expandBS()
            }
        } else {
            waitForState()
        }

        val movieParams = requireArguments().getParcelable<MovieFragmentParams>(BUNDLE_KEY)
        movieParams?.let {
            viewModel.launchMovieDetailed(movieParams.movieId)
            requireArguments().remove(BUNDLE_KEY)
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
            }
        )
        binding.bottomSheet.setRecyclerAdapter(movieDetailsAdapter)
        lifecycle.addObserver(binding.bottomSheet)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.apply {
                movieData?.let { movieDetailedData ->
                    movieDetailsAdapter.submitList(movieDetailsAdapterModelList)
                    binding.bottomSheet.setMovieItem(movieDetailedData)
                }
                toast?.let { message ->
                    showToast(message)
                }
            }
        }
    }

    private fun waitForState() {
        val observer = viewModel.state
        observer.observe(viewLifecycleOwner) {
            halfExpandBS()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SHEET_STATE, getCurrentState())
    }

    private fun backgroundPosterAlphaBehaviour(alpha: Float) {
        binding.bottomSheet.backgroundPoster.apply {
            visibility = View.VISIBLE
            this.alpha = alpha
        }
    }

    private fun getBehavior(): BottomSheetBehavior<*> {
        return (binding.bottomSheet.layoutParams as CoordinatorLayout.LayoutParams).behavior
            as BottomSheetBehavior<*>
    }

    private fun enableScroll() {
        isScrollEnabled = true
        binding.bottomSheet.recycler.isNestedScrollingEnabled = true
    }

    private fun disableScroll() {
        isScrollEnabled = false
        binding.bottomSheet.recycler.isNestedScrollingEnabled = false
    }

    private fun enableDragging() {
        isDraggingEnabled = true
        getBehavior().isDraggable = true
    }

    private fun disableDragging() {
        getBehavior().isDraggable = false
        isDraggingEnabled = false
    }

    // closes fragment by calling router.back() in onHide callback
    private fun closeFragment() {
        hideBS()
    }

    private fun showToast(message: String) {
        router.show(
            NotificationType.Toast(message)
        ).also {
            viewModel.cleanToast()
        }
    }

    override fun onBackPress() {
        closeFragment()
    }

    // closes fragment by the router for popup animation
    override fun onHide() {
        binding.apply {
            blackBackgroundFrameLayout.isClickable = false
            disableScroll()
            enableDragging()
        }
        router.back()
    }

    override fun onExpand() {
        disableDragging()
        enableScroll()
        binding.bottomSheet.toolbar.setNavigationOnClickListener {
            closeFragment()
        }
    }

    override fun onHalfExpand() {
        binding.blackBackgroundFrameLayout.apply {
            alpha = 0.5f
            isClickable = true
            setOnClickListener { view ->
                view.isClickable = false
                closeFragment()
            }
        }
        disableScroll()
    }

    override fun onSlide(offset: Float) {
        binding.blackBackgroundFrameLayout.alpha = offset
        backgroundPosterAlphaBehaviour(offset - 0.5f)
    }

    companion object {
        private const val SHEET_STATE = "movie_fragment_sheet_state"
        private const val APPBAR_STATE = "movie_fragment_app_bar_state"
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
