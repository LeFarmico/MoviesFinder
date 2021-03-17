package com.lefarmico.moviesfinder.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.animations.FabMenuAnimator
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.models.MovieItem
import com.lefarmico.moviesfinder.presenters.MainActivityPresenter
import com.lefarmico.moviesfinder.view.MainActivityView
import javax.inject.Inject

class MainActivity @Inject constructor() : AppCompatActivity(), MainActivityView {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var presenter: MainActivityPresenter
    @Inject override lateinit var interactor: Interactor

    lateinit var activeFragment: Fragment

    val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attachView(this)

        if (savedInstanceState == null) {
            presenter.launchFragments()
            presenter.showFragment(presenter.fragmentsMap["MovieFragment"]!!)
        }

        launchBottomSheet()
        launchFabMenu()

        binding.bottomNavigationBarView
            .setOnNavigationItemSelectedListener(setMenuChangeListener())
    }

    private fun launchBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            skipCollapsed = true
            state = BottomSheetBehavior.STATE_HIDDEN

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        binding.blackBackgroundFrameLayout.isClickable = false
                        binding.bottomNavigationBarView.visibility = View.VISIBLE
                    } else if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                        binding.blackBackgroundFrameLayout.isClickable = true
                        binding.blackBackgroundFrameLayout.setOnClickListener {
                            state = BottomSheetBehavior.STATE_HIDDEN
                            binding.blackBackgroundFrameLayout.isClickable = false
                        }
                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    binding.blackBackgroundFrameLayout.alpha = 0 + slideOffset
                }
            })
        }
    }

    private fun setMenuChangeListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movies_menu -> {
                    val tag = "MovieFragment"
                    presenter.showFragment(presenter.fragmentsMap[tag]!!)
                    true
                }
                R.id.series_menu -> {
                    val tag = "SeriesFragment"
                    presenter.showFragment(presenter.fragmentsMap[tag]!!)
                    true
                }
                R.id.favorites_menu -> {
                    val tag = "FavoritesFragment"
                    presenter.showFragment(presenter.fragmentsMap[tag]!!)
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
            .add(R.id.nav_host_fragment, fragment, tag)
            .hide(fragment)
            .commit()
    }

    override fun showFragment(fragment: Fragment) {
        if (!this::activeFragment.isInitialized) {
            activeFragment = fragment
        }
        supportFragmentManager
            .beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()
        activeFragment = fragment
    }

    override fun hideFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .hide(fragment)
            .commit()
    }

    override fun launchItemDetails(movieItem: MovieItem) {
        binding.bottomSheet.apply {
            setRate(5)
            setProviders(movieItem.watchProviders)
            setGenres(movieItem.genres)
            setTitle(movieItem.title)
            setRating(movieItem.rating)
            setDescription(movieItem.description)
            setPoster(movieItem.posterPath)
        }
        BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_HALF_EXPANDED
        binding.bottomNavigationBarView.visibility = View.INVISIBLE
    }

    override fun launchFabMenu() {
        FabMenuAnimator(
            binding.fabMenu.fabMovies,
            binding.fabMenu.fabSeries,
            binding.fabMenu.fabFavorites,
        ).apply {
            setAnimator(200)
            binding.fab.setOnClickListener {
                onMenuClick()
            }
        }
    }

    override fun showError() {
        binding.apply {
            errorTextView.visibility = View.VISIBLE
            navHostFragment.visibility = View.INVISIBLE
        }
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun endLoading() {
        TODO("Not yet implemented")
    }
}
