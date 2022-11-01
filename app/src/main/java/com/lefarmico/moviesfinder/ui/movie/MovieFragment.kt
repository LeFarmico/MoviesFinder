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
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.FragmentMovieBinding
import com.lefarmico.moviesfinder.ui.base.BaseFragment
import com.lefarmico.moviesfinder.ui.common.adapter.MovieDetailsAdapter
import com.lefarmico.moviesfinder.ui.common.adapter.decorator.MovieDetailsDecorator
import com.lefarmico.moviesfinder.ui.common.delegation.OnBackPressListener
import com.lefarmico.moviesfinder.ui.common.delegation.OnBackPressListenerImpl
import com.lefarmico.moviesfinder.ui.navigation.api.NotificationType
import com.lefarmico.moviesfinder.ui.navigation.api.Router
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination
import com.lefarmico.moviesfinder.ui.navigation.api.params.MovieFragmentParams
import com.lefarmico.moviesfinder.utils.component.appBar.AppBarStateChangeListener
import com.lefarmico.moviesfinder.utils.component.bottomSheet.BottomSheetBehaviourHandler
import com.lefarmico.moviesfinder.utils.component.bottomSheet.BottomSheetBehaviourHandlerImpl
import com.lefarmico.moviesfinder.utils.component.bottomSheet.BottomSheetStateListener
import com.lefarmico.moviesfinder.utils.extension.getArgumentsData
import com.lefarmico.moviesfinder.utils.extension.getDrawable
import com.lefarmico.moviesfinder.utils.extension.removeArgument
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment :
    BaseFragment<MovieViewModel, FragmentMovieBinding>(),
    BottomSheetStateListener,
    OnBackPressListener by OnBackPressListenerImpl(),
    BottomSheetBehaviourHandler by BottomSheetBehaviourHandlerImpl() {

    @Inject lateinit var router: Router

    private var isScrollEnabled = true
    private var isDraggingEnabled = true

    private lateinit var movieDetailsAdapter: MovieDetailsAdapter

    // UI element state
    private var appBarInternalState: AppBarStateChangeListener.State = AppBarStateChangeListener.State.Idle
    private var bottomSheetState: Int = BottomSheetBehavior.STATE_SETTLING

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

        bindBottomSheet(binding.bottomSheet, this.lifecycle, this)

        // onBackPress registration
        registerOnBackPress(requireActivity(), this.lifecycle) {
            closeFragment()
        }

        // AppBar layout behaviour
        val layoutParams = binding.bottomSheet.appBar.layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            layoutParams.apply {
                if (behavior == null)
                    behavior = AppBarLayout.Behavior()

                (behavior as AppBarLayout.Behavior)
                    .setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                        override fun canDrag(appBarLayout: AppBarLayout): Boolean = isScrollEnabled
                    })
            }
        }

        // AppBar State change listener
        binding.bottomSheet.appBar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State) {
                appBarInternalState = state
            }
        })

        try {
            savedInstanceState!!.getParcelable<MovieElementState>(BUNDLE_STATE)!!.also {
                recoverState(it)
            }.also {
                removeArgument(BUNDLE_KEY)
            }
        } catch (e: NullPointerException) {
            getArgumentsData<MovieFragmentParams>(BUNDLE_KEY)
                ?.let { params ->
                    getNewState(params.movieId)
                }
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
        binding.bottomSheet.recycler.apply {
            adapter = movieDetailsAdapter
            addItemDecoration(
                MovieDetailsDecorator(
                    horizontalPd = R.dimen.stnd_margin.pixelOffset,
                    betweenLinePd = R.dimen.stnd_between_line_space.pixelOffset,
                    headerBottomPd = R.dimen.stnd_very_small_margin.pixelOffset
                ),
                0
            )
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.movieData?.let { movieDetailedData ->
                movieDetailsAdapter.submitList(state.movieDetailsAdapterModelList)
                binding.bottomSheet.apply {
                    collapsingToolbarLayout.title = movieDetailedData.title
                    setBackgroundPoster(movieDetailedData.posterPath)
                    setForegroundPoster(movieDetailedData.posterPath)
                }
            }
            state.toast?.let { message ->
                showToast(message)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val movieElementState = MovieElementState(
            bottomSheetState,
            binding.blackBackgroundFrameLayout.alpha,
            appBarInternalState
        )
        outState.putParcelable(
            BUNDLE_STATE,
            movieElementState
        )
    }

    // closes fragment by the router for popup animation
    override fun onHide() {
        bottomSheetState = BottomSheetBehavior.STATE_HIDDEN
        binding.apply {
            blackBackgroundFrameLayout.isClickable = false
            enableScrolling(false)
            enableDragging(true)
        }
        router.back()
    }

    override fun onExpand() {
        bottomSheetState = BottomSheetBehavior.STATE_EXPANDED
        enableDragging(false)
        enableScrolling(true)
        binding.bottomSheet.toolbar.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back_24)
        binding.bottomSheet.toolbar.setNavigationOnClickListener {
            closeFragment()
        }
    }

    override fun onHalfExpand() {
        bottomSheetState = BottomSheetBehavior.STATE_HALF_EXPANDED
        enableScrolling(false)
        enableDragging(true)
        binding.bottomSheet.toolbar.navigationIcon = null
        binding.blackBackgroundFrameLayout.setOnClickListener {
            closeFragment()
        }
    }

    override fun onSlide(offset: Float) {
        setBackgroundAlpha(offset)
    }

    private fun recoverState(movieElementState: MovieElementState) {
        when (movieElementState.sheetState) {
            BottomSheetBehavior.STATE_HALF_EXPANDED -> halfExpandBottomSheet()
            BottomSheetBehavior.STATE_EXPANDED -> expandBottomSheet()
        }
        when (movieElementState.appBarState) {
            AppBarStateChangeListener.State.Collapsed -> binding.bottomSheet.appBar.setExpanded(false, false)
            AppBarStateChangeListener.State.Expanded -> binding.bottomSheet.appBar.setExpanded(true, false)
            AppBarStateChangeListener.State.Idle -> {}
        }

        setBackgroundAlpha(movieElementState.backgroundAlpha)
    }

    private fun getNewState(movieId: Int) {
        viewModel.launchMovieDetailed(movieId)
        val observer = viewModel.state
        observer.observe(viewLifecycleOwner) {
            halfExpandBottomSheet()
        }
    }

    private fun setBackgroundAlpha(alpha: Float) {
        binding.apply {
            bottomSheet.backgroundPoster.alpha = alpha - 0.5f
            blackBackgroundFrameLayout.alpha = alpha
        }
    }

    private fun enableScrolling(isEnable: Boolean) {
        isScrollEnabled = isEnable
        binding.bottomSheet.recycler.isNestedScrollingEnabled = isEnable
    }

    private fun enableDragging(isEnabled: Boolean) {
        val layoutParams = (binding.bottomSheet.layoutParams as CoordinatorLayout.LayoutParams)
        val behavior = layoutParams.behavior as BottomSheetBehavior<*>
        behavior.isDraggable = isEnabled
        isDraggingEnabled = isEnabled
    }

    // closes fragment by calling router.back() in onHide callback
    private fun closeFragment() {
        hideBottomSheet()
    }

    private fun showToast(message: String) {
        router.show(
            NotificationType.Toast(message)
        ).also {
            viewModel.cleanToast()
        }
    }

    private val Int.pixelOffset: Int get() = requireContext().resources.getDimensionPixelOffset(this)

    companion object {
        private const val BUNDLE_STATE = "movie_fragment_sheet_state"
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

@Parcelize
data class MovieElementState(
    val sheetState: Int,
    val backgroundAlpha: Float,
    val appBarState: AppBarStateChangeListener.State
) : Parcelable
