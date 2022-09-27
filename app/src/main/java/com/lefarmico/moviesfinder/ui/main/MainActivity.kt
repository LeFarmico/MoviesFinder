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
import com.lefarmico.moviesfinder.ui.common.adapter.widgetAdapter.WidgetAdapter
import com.lefarmico.moviesfinder.ui.common.adapter.widgetAdapter.widget.WidgetModel
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

    private val widgetAdapter = WidgetAdapter()

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
        binding.bottomSheet.setRecyclerAdapter(widgetAdapter)
        applyBottomSheetStateCallbacks()
        lifecycle.addObserver(binding.bottomSheet)

        viewModel.shownMovieLiveData.observe(this) {
            launchItemDetails(it)
            widgetAdapter.submitList(
                listOf(
                    WidgetModel.MovieInfoOverview(
                        genres = it.genres.reduce { acc, s -> "$acc / $s" },
                        length = "Length: ${it.length} min",
                        releaseDate = it.releaseDate
                    ),
                    WidgetModel.RatingOverview(
                        usesRating = it.rating,
                        userRating = it.yourRate,
                        isWatchList = it.isWatchlist
                    ),
                    WidgetModel.WhereToWatch(
                        providerList = it.watchMovieProviderData
                    ),
                    WidgetModel.HeaderAndTextExpandable(
                        header = getString(R.string.storyline),
                        description = it.description
                    ),
                    WidgetModel.CastAndCrewWidgetModel(
                        castHeader = getString(R.string.cast),
                        crewHeader = getString(R.string.crew),
                        castList = it.actors ?: listOf(),
                        crewList = it.directors ?: listOf()
                    )
                )
            )
        }

        viewModel.toastLiveData.observe(this) {
            it?.let { showToast(it) }
        }

        viewModel.recommendationsLiveData.observe(this) {
//            binding.bottomSheet.setRecommendations(it)
        }
        viewModel.startObserveMovieDetailedFromChannel()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun applyBottomSheetStateCallbacks() {
        binding.bottomSheet.apply {

//            watchListCallback(
//                onChecked = {
//                    viewModel.tryToSaveMovieToWatchlist()
//                },
//                notChecked = {
//                    viewModel.tryToRemoveMovieFromWatchlist()
//                }
//            )

            onSlide = { slideOffset ->
                binding.blackBackgroundFrameLayout.alpha = slideOffset
            }
            onHidden = {
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
                disableDragging()
                enableScroll()
                onNavigateUpPressed {
                    it.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }
    }

    private fun launchItemDetails(movieDetailedData: MovieDetailedData) {
        viewModel.getRecommendations(movieDetailedData.movieId)
        binding.bottomSheet.setMovieItem(movieDetailedData)
        binding.bottomSheet.getBehavior().state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show().also {
            viewModel.cleanToast()
        }
    }
}
