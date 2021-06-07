package com.lefarmico.moviesfinder.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.customViews.BottomSheetMovieDetails
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.MovieItem
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.fragments.SearchFragment
import com.lefarmico.moviesfinder.view.MainActivityView
import com.lefarmico.moviesfinder.viewModels.MainActivityViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainActivity @Inject constructor() : AppCompatActivity(), MainActivityView {

    private lateinit var binding: ActivityMainBinding
    @Inject override lateinit var interactor: Interactor

    val viewModel: MainActivityViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    private lateinit var fragmentsList: List<Pair<String, Fragment>>
    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<BottomSheetMovieDetails>

    private val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationBarView.setupWithNavController(navController)
        appBarConfig = AppBarConfiguration(
            setOf(R.id.movies_menu, R.id.series_menu, R.id.favorites_menu),
        )
        setupActionBarWithNavController(navController, appBarConfig)

        bottomSheetBehaviour = BottomSheetBehavior.from(binding.bottomSheet)
        fragmentsList = viewModel.fragmentsList

        viewModel.movieDetails
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                launchItemDetails(it)
            }

        applyBottomSheetStateCallbacks()
        binding.searchFab.setOnClickListener {
            launchFragmentWithBackStack(SearchFragment(), "SearchFragment")
        }

//        // TODO : Понять как лучше сделать
//        viewModel.fragmentLiveData.observe(this) {
//            launchFragment(it.second, it.first)
//        }
//        binding.bottomNavigationBarView.setOnNavigationItemSelectedListener(setNavigationListener())
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun applyBottomSheetStateCallbacks() {
        bottomSheetBehaviour.apply {
            skipCollapsed = true
            state = BottomSheetBehavior.STATE_HIDDEN

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    binding.blackBackgroundFrameLayout.alpha = 0 + slideOffset
                    if (slideOffset >= 0.5) {
                        binding.bottomSheet.setAlphaParamsListener(slideOffset - 0.5f)
                    }
                }
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            binding.apply {
                                isDraggable = true
                                blackBackgroundFrameLayout.isClickable = false
                                bottomNavigationBarView.visibility = View.VISIBLE
                            }
                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            binding.apply {
                                bottomNavigationBarView.visibility = View.INVISIBLE
                                blackBackgroundFrameLayout.isClickable = true
                                blackBackgroundFrameLayout.setOnClickListener {
                                    it.isClickable = false
                                    state = BottomSheetBehavior.STATE_HIDDEN
                                }
                            }
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            isDraggable = false
                            binding.bottomSheet.backButton.apply {
                                isClickable = true
                                setOnClickListener { state = BottomSheetBehavior.STATE_HIDDEN }
                            }
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            binding.bottomSheet.backButton.isClickable = false
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {}
                        BottomSheetBehavior.STATE_SETTLING -> {}
                    }
                }
            })
        }
    }

    override fun launchFragmentWithBackStack(fragment: Fragment, tag: String) {
        Log.d("LaunchFragment", "$fragment")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    private fun setNavigationListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movies_menu -> {
                    viewModel.postFragment(fragmentsList[0])
                    true
                }
                R.id.series_menu -> {
                    viewModel.postFragment(fragmentsList[1])
                    true
                }
                R.id.favorites_menu -> {
                    viewModel.postFragment(fragmentsList[2])
                    true
                }
                else -> false
            }
        }
    }

    override fun launchFragment(fragment: Fragment, tag: String) {
        Log.d("LaunchFragment", tag)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, tag)
            .commit()
    }

    override fun launchItemDetails(movieItem: MovieItem) {
        binding.bottomSheet.apply {
            setMovieItem(movieItem)
            watchListCallback(
                { viewModel.watchlistHandler(movieItem, true) },
                { viewModel.watchlistHandler(movieItem, false) }
            )
        }
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    override fun showError() {
        binding.apply {
            errorTextView.visibility = View.VISIBLE
            navHostFragment.visibility = View.INVISIBLE
        }
    }
}
