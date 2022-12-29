package com.lefarmico.moviesfinder.ui.movie

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.FragmentMovieBinding
import com.lefarmico.moviesfinder.ui.common.adapter.MovieDetailsAdapter
import com.lefarmico.moviesfinder.ui.common.adapter.decorator.MovieDetailsDecorator
import com.lefarmico.moviesfinder.ui.delegation.appBarDragCallback.AppBarDragCallbackDelegation
import com.lefarmico.moviesfinder.ui.delegation.appBarDragCallback.AppBarDragCallbackDelegationImpl
import com.lefarmico.moviesfinder.ui.delegation.appBarListener.AppBarStateHandlerDelegation
import com.lefarmico.moviesfinder.ui.delegation.appBarListener.AppBarStateHandlerDelegationImpl
import com.lefarmico.moviesfinder.ui.delegation.onBackPress.OnBackPressDelegation
import com.lefarmico.moviesfinder.ui.delegation.onBackPress.OnBackPressDelegationImpl
import com.lefarmico.moviesfinder.ui.navigation.api.NotificationType
import com.lefarmico.moviesfinder.ui.navigation.api.Router
import com.lefarmico.moviesfinder.ui.navigation.api.ScreenDestination
import com.lefarmico.moviesfinder.ui.navigation.api.params.MovieFragmentParams
import com.lefarmico.moviesfinder.utils.component.appBar.AppBarStateChangeListener
import com.lefarmico.moviesfinder.utils.component.bottomSheet.BottomSheetBehaviourController
import com.lefarmico.moviesfinder.utils.component.bottomSheet.BottomSheetBehaviourControllerImpl
import com.lefarmico.moviesfinder.utils.component.bottomSheet.BottomSheetStateListener
import com.lefarmico.moviesfinder.utils.extension.getArgumentsData
import com.lefarmico.moviesfinder.utils.extension.getDrawable
import com.lefarmico.moviesfinder.utils.extension.removeArgument
import com.lefarmico.moviesfinder.utils.logger.LifecycleLogger
import com.lefarmico.moviesfinder.utils.logger.LifecycleLoggerImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class MovieScreenElementsState(
    val sheetState: Int = BottomSheetBehavior.STATE_SETTLING,
    val backgroundAlpha: Float = 0f,
    val appBarState: AppBarStateChangeListener.State = AppBarStateChangeListener.State.Idle,
    val isDraggable: Boolean = true,
    val isScrollable: Boolean = false
) : Parcelable

@AndroidEntryPoint
class MovieFragment :
    Fragment(),
    BottomSheetStateListener,
    AppBarStateHandlerDelegation by AppBarStateHandlerDelegationImpl(),
    AppBarDragCallbackDelegation by AppBarDragCallbackDelegationImpl(),
    OnBackPressDelegation by OnBackPressDelegationImpl(),
    BottomSheetBehaviourController by BottomSheetBehaviourControllerImpl(),
    LifecycleLogger by LifecycleLoggerImpl() {

    private lateinit var _binding: FragmentMovieBinding
    private val binding get() = _binding

    @Inject
    lateinit var router: Router

    val viewModel: MovieViewModel by viewModels()

    private lateinit var movieDetailsAdapter: MovieDetailsAdapter

    private var sheetState: Int = BottomSheetBehavior.STATE_SETTLING
    private var backgroundAlpha: Float = 0f
    private var appBarState: AppBarStateChangeListener.State = AppBarStateChangeListener.State.Idle
    private var isDraggable: Boolean = true
    private var isScrollable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerLifecycleLogger(this.lifecycle, this::class.java, "MovieFragment", true)

        try {
            // getting the saved state
            savedInstanceState!!
                .getParcelable<MovieScreenElementsState>(BUNDLE_STATE)
                .let { state ->
                    recoverState(state!!)
                    removeArgument(BUNDLE_KEY)
                }
        } catch (e: NullPointerException) {
            // getting a new state
            getArgumentsData<MovieFragmentParams>(BUNDLE_KEY) { params ->
                if (params == null) return@getArgumentsData
                viewModel.launchMovieDetailed(params.movieId)
            }
        }

        // register BottomSheet handler
        registerBottomSheetHandler(binding.bottomSheet, this.lifecycle, this)

        // onBackPress registration
        registerOnBackPress(requireActivity(), this.lifecycle) {
            closeFragment()
        }

        // AppBar State change handler
        registerAppBarStateChangeHandler(
            binding.bottomSheet.appBar,
            this.lifecycle
        ) { state ->
            appBarState = state
        }

        // AppBar drag availability
        registerDragCallback(
            binding.bottomSheet.appBar,
            this.lifecycle
        ) { isScrollable }

        // set up BottomSheet state
        when (sheetState) {
            BottomSheetBehavior.STATE_HALF_EXPANDED -> halfExpandBottomSheet()
            BottomSheetBehavior.STATE_EXPANDED -> expandBottomSheet()
        }
        // set up AppBar state
        when (appBarState) {
            AppBarStateChangeListener.State.Collapsed -> binding.bottomSheet.appBar.setExpanded(false, false)
            AppBarStateChangeListener.State.Expanded -> binding.bottomSheet.appBar.setExpanded(true, false)
            AppBarStateChangeListener.State.Idle -> { /* waiting for the first state */ }
        }
        // set up Scrollable of view
        binding.bottomSheet.recycler.isNestedScrollingEnabled = isScrollable

        // set up Draggable of the view
        enableDragging(isDraggable)

        // set up background shade
        setBackgroundAlpha(backgroundAlpha)

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
                    ScreenDestination.MovieToSelf,
                    MovieFragmentParams(it.movieId)
                )
            }
        )
        binding.bottomSheet.recycler.apply {
            adapter = movieDetailsAdapter
            isNestedScrollingEnabled = isScrollable
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
            if (state.movieDetailsModelList != null &&
                state.movieData != null
            ) {
                if (sheetState == BottomSheetBehavior.STATE_SETTLING) {
                    halfExpandBottomSheet()
                }
                movieDetailsAdapter.submitList(state.movieDetailsModelList)
                binding.bottomSheet.apply {
                    collapsingToolbarLayout.title = state.movieData.title
                    setBackgroundPoster(state.movieData.posterPath)
                    setForegroundPoster(state.movieData.posterPath)
                }
            }

            if (state.isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.event.observe(viewLifecycleOwner) { event ->
            router.show(NotificationType.Toast(event))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(
            BUNDLE_STATE,
            createElementState()
        )
    }

    // closes fragment by the router for popup animation
    override fun onHide() {
        sheetState = BottomSheetBehavior.STATE_HIDDEN
        isScrollable = false
        isDraggable = true
        binding.blackBackgroundFrameLayout.isClickable = false
        router.back()
    }

    override fun onExpand() {
        // UI Element state changing
        sheetState = BottomSheetBehavior.STATE_EXPANDED
        isScrollable = true
        isDraggable = false

        binding.bottomSheet.recycler.isNestedScrollingEnabled = true
        binding.bottomSheet.toolbar.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back_24)
        binding.bottomSheet.toolbar.setNavigationOnClickListener {
            closeFragment()
        }

        binding.bottomSheet.recycler.isNestedScrollingEnabled = isScrollable
        enableDragging(isDraggable)
    }

    override fun onHalfExpand() {
        // UI Element state changing
        sheetState = BottomSheetBehavior.STATE_HALF_EXPANDED
        isScrollable = false
        isDraggable = true

        binding.bottomSheet.toolbar.navigationIcon = null
        binding.blackBackgroundFrameLayout.setOnClickListener {
            closeFragment()
        }

        binding.bottomSheet.recycler.isNestedScrollingEnabled = isScrollable
        enableDragging(isDraggable)
    }

    override fun onSlide(offset: Float) {
        setBackgroundAlpha(offset)
    }

    private fun recoverState(movieScreenElementsState: MovieScreenElementsState) {
        appBarState = movieScreenElementsState.appBarState
        sheetState = movieScreenElementsState.sheetState
        backgroundAlpha = movieScreenElementsState.backgroundAlpha
        isScrollable = movieScreenElementsState.isScrollable
        isDraggable = movieScreenElementsState.isDraggable
    }

    private fun setBackgroundAlpha(alpha: Float) {
        backgroundAlpha = alpha
        binding.apply {
            bottomSheet.backgroundPoster.alpha = alpha - 0.5f
            blackBackgroundFrameLayout.alpha = alpha
        }
    }

    private fun enableDragging(isEnabled: Boolean) {
        val layoutParams = binding.bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = layoutParams.behavior as BottomSheetBehavior<*>
        behavior.isDraggable = isEnabled
    }

    // closes fragment by calling router.back() in onHide callback
    private fun closeFragment() {
        hideBottomSheet()
    }

    private fun createElementState() = MovieScreenElementsState(
        sheetState, backgroundAlpha, appBarState, isDraggable, isScrollable
    )

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
