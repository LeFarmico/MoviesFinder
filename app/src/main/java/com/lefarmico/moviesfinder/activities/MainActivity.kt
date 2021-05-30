package com.lefarmico.moviesfinder.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.MovieItem
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.fragments.SearchFragment
import com.lefarmico.moviesfinder.view.MainActivityView
import com.lefarmico.moviesfinder.viewModels.MainActivityViewModel
import javax.inject.Inject

class MainActivity @Inject constructor() : AppCompatActivity(), MainActivityView {

    private lateinit var binding: ActivityMainBinding
    @Inject override lateinit var interactor: Interactor

    val viewModel: MainActivityViewModel by viewModels()

    private lateinit var fragmentsList: List<Pair<String, Fragment>>

    private val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        fragmentsList = viewModel.fragmentsList

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            viewModel.postFragmentData(fragmentsList[0])
        }
        viewModel.movieDetails.observe(this) {
            launchItemDetails(it)
        }
        viewModel.fragmentData.observe(this) {
            launchFragment(it.second, it.first)
        }

        launchBottomSheet()
        binding.searchFab.setOnClickListener {
            launchSearchViewListener()
        }

        binding.bottomNavigationBarView
            .setOnNavigationItemSelectedListener(setMenuChangeListener())
    }

    private fun launchBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            skipCollapsed = true
            state = BottomSheetBehavior.STATE_HIDDEN

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            binding.blackBackgroundFrameLayout.isClickable = false
                            binding.bottomNavigationBarView.visibility = View.VISIBLE
                            isDraggable = true
                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            binding.blackBackgroundFrameLayout.isClickable = true
                            binding.blackBackgroundFrameLayout.setOnClickListener {
                                state = BottomSheetBehavior.STATE_HIDDEN
                                binding.blackBackgroundFrameLayout.isClickable = false
                            }
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            binding.bottomSheet.backButton.isClickable = true
                            binding.bottomSheet.backButton.setOnClickListener {
                                state = BottomSheetBehavior.STATE_HIDDEN
                            }
                            isDraggable = false
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            binding.bottomSheet.backButton.isClickable = false
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {}
                        BottomSheetBehavior.STATE_SETTLING -> {}
                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    binding.blackBackgroundFrameLayout.alpha = 0 + slideOffset
                    if (slideOffset >= 0.5) {
                        binding.bottomSheet.backgroundPoster.alpha = slideOffset - 0.5f
                        binding.bottomSheet.backButton.alpha = slideOffset - 0.5f
                    }
                }
            })
        }
    }

    private fun launchSearchViewListener() {
        Log.d("LaunchFragment", "SearchFragment")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, SearchFragment(), "SearchFragment")
            .addToBackStack("SearchFragment")
            .commit()
    }

    private fun setMenuChangeListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movies_menu -> {
                    viewModel.postFragmentData(fragmentsList[0])
                    true
                }
                R.id.series_menu -> {
                    viewModel.postFragmentData(fragmentsList[1])
                    true
                }
                R.id.favorites_menu -> {
                    viewModel.postFragmentData(fragmentsList[2])
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
            setRate(5)
            setReleaseDate(movieItem.releaseDate)
            setGenres(movieItem.genres)
            setTitle(movieItem.title)
            setRating(movieItem.rating)
            setDescription(movieItem.description)
            setPoster(movieItem.posterPath)
            setSpinnerProvider(movieItem.watchProviders)
            setBackground(movieItem.posterPath)
            setActors(movieItem.actors)
            setLength(movieItem.length)
            setWatchlist(movieItem.isWatchlist)
            isWatchlist.setOnClickListener {
                if (isWatchlist.isChecked) {
                    viewModel.watchlistChanger(movieItem, true)
                } else {
                    viewModel.watchlistChanger(movieItem, false)
                }
            }
        }
        BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_HALF_EXPANDED
        binding.bottomNavigationBarView.visibility = View.INVISIBLE
    }

    override fun showError() {
        binding.apply {
            errorTextView.visibility = View.VISIBLE
            navHostFragment.visibility = View.INVISIBLE
        }
    }
}
