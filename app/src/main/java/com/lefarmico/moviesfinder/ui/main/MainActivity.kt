package com.lefarmico.moviesfinder.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.ui.base.BaseActivity
import com.lefarmico.moviesfinder.ui.main.adapter.MovieDetailsAdapter
import com.lefarmico.moviesfinder.ui.main.adapter.model.MovieDetailsModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun getInjectViewModel(): MainViewModel {
        val viewModel: MainViewModel by viewModels()
        return viewModel
    }

    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    private val movieDetailsAdapter = MovieDetailsAdapter { isChecked ->
        if (isChecked) {
            viewModel.tryToSaveMovieToWatchlist()
        } else {
            viewModel.tryToRemoveMovieFromWatchlist()
        }
    }

    private val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationBarView.setupWithNavController(navController)
        appBarConfig = AppBarConfiguration(
            setOf(R.id.movies_menu, R.id.favorites_menu)
        )
        setupActionBarWithNavController(navController, appBarConfig)

        binding.bottomSheet.bindBottomSheetBehaviour()
        binding.bottomSheet.setRecyclerAdapter(movieDetailsAdapter)
        applyBottomSheetStateCallbacks()
        lifecycle.addObserver(binding.bottomSheet)

        viewModel.startObserveMovieDetailedFromChannel()
        viewModel.state.observe(this) { state ->
            state.apply {
                shownMovie?.let {
                    launchItemDetails(it.movieData, it.movieDetailsModelList)
                }
                toast?.let {
                    showToast(it)
                }
                bottomSheetState.apply {
                    val bottomSheetState = when (this) {
                        MainState.BottomSheetState.Expanded -> BottomSheetBehavior.STATE_EXPANDED
                        MainState.BottomSheetState.HalfExpanded -> BottomSheetBehavior.STATE_HALF_EXPANDED
                        MainState.BottomSheetState.Hidden -> BottomSheetBehavior.STATE_HIDDEN
                    }
                    binding.bottomSheet.getBehavior().state = bottomSheetState
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun applyBottomSheetStateCallbacks() {
        binding.bottomSheet.apply {

            onSlide = { slideOffset ->
                binding.blackBackgroundFrameLayout.alpha = slideOffset
            }
            onHidden = {
                viewModel.setBottomSheetState(MainState.BottomSheetState.Hidden)
                binding.apply {
                    blackBackgroundFrameLayout.isClickable = false
                    bottomNavigationBarView.visibility = View.VISIBLE
                    disableScroll()
                    enableDragging()
                }
            }
            onHalfExpanded = { behavior ->
                binding.apply {
                    bottomNavigationBarView.visibility = View.INVISIBLE
                    blackBackgroundFrameLayout.isClickable = true
                    blackBackgroundFrameLayout.setOnClickListener { view ->
                        view.isClickable = false
                        behavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                }
                disableScroll()
            }
            onExpanded = {
                viewModel.setBottomSheetState(MainState.BottomSheetState.Expanded)
                disableDragging()
                enableScroll()
                onNavigateUpPressed {
                    it.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }
    }

    private fun launchItemDetails(movieDetailedData: MovieDetailedData, movieDetailsModelList: List<MovieDetailsModel>) {
        movieDetailsAdapter.submitList(movieDetailsModelList)
        binding.bottomSheet.setMovieItem(movieDetailedData)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show().also {
            viewModel.cleanToast()
        }
    }
}
