package com.lefarmico.moviesfinder.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.ui.common.widget.BottomSheetMovieDetails

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val viewModel: MainActivityViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<BottomSheetMovieDetails>

    private val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setSupportActionBar(binding.toolbar)
//
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController
//
//        binding.bottomNavigationBarView.setupWithNavController(navController)
//        appBarConfig = AppBarConfiguration(
//            setOf(R.id.movies_menu, R.id.favorites_menu),
//        )
//        setupActionBarWithNavController(navController, appBarConfig)
//
//        bottomSheetBehaviour = BottomSheetBehavior.from(binding.bottomSheet)
//
//        viewModel.movieDataDetails
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                launchItemDetails(it)
//            }
//
//        applyBottomSheetStateCallbacks()
        binding.searchFab.setOnClickListener {
//            navController.navigate(R.id.searchFragment)
            Toast.makeText(this, "Update to pro", Toast.LENGTH_SHORT).show()
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp()
//    }
//
//    private fun applyBottomSheetStateCallbacks() {
//        bottomSheetBehaviour.apply {
//            skipCollapsed = true
//            state = BottomSheetBehavior.STATE_HIDDEN
//
//            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                    binding.blackBackgroundFrameLayout.alpha = 0 + slideOffset
//                    if (slideOffset >= 0.5) {
//                        binding.bottomSheet.setAlphaParamsListener(slideOffset - 0.5f)
//                    }
//                }
//                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    when (newState) {
//                        BottomSheetBehavior.STATE_HIDDEN -> {
//                            binding.apply {
//                                isDraggable = true
//                                blackBackgroundFrameLayout.isClickable = false
//                                bottomNavigationBarView.visibility = View.VISIBLE
//                            }
//                        }
//                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
//                            binding.apply {
//                                bottomNavigationBarView.visibility = View.INVISIBLE
//                                blackBackgroundFrameLayout.isClickable = true
//                                blackBackgroundFrameLayout.setOnClickListener {
//                                    it.isClickable = false
//                                    state = BottomSheetBehavior.STATE_HIDDEN
//                                }
//                            }
//                        }
//                        BottomSheetBehavior.STATE_EXPANDED -> {
//                            isDraggable = false
//                            binding.bottomSheet.backButton.apply {
//                                isClickable = true
//                                setOnClickListener { state = BottomSheetBehavior.STATE_HIDDEN }
//                            }
//                        }
//                        BottomSheetBehavior.STATE_DRAGGING -> {
//                            binding.bottomSheet.backButton.isClickable = false
//                        }
//                        BottomSheetBehavior.STATE_COLLAPSED -> {}
//                        BottomSheetBehavior.STATE_SETTLING -> {}
//                    }
//                }
//            })
//        }
//    }
//
//    private fun launchItemDetails(movieItem: MovieData) {
//        binding.bottomSheet.apply {
//            setMovieItem(movieItem)
//            watchListCallback(
//                { viewModel.watchlistHandler(movieItem, true) },
//                { viewModel.watchlistHandler(movieItem, false) }
//            )
//        }
//        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//    }
}
