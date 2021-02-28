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
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.fragments.FavoritesFragment
import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.fragments.SeriesFragment
import com.lefarmico.moviesfinder.models.Item
import com.lefarmico.moviesfinder.presenters.MainActivityPresenter
import com.lefarmico.moviesfinder.private.PrivateData
import com.lefarmico.moviesfinder.view.MainView
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MainActivity @Inject constructor() : AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var presenter: MainActivityPresenter

    override val fragmentManager = supportFragmentManager

    val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attachView(this)

        if (savedInstanceState == null)
            presenter.launchFragment(MovieFragment(), "MovieFragment")

        launchBottomSheet()
        launchFabMenu()

        binding.bottomNavigationBarView
            .setOnNavigationItemSelectedListener(setMenuChangeListener())
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

    private fun setMenuChangeListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movies_menu -> {
                    val tag = "MovieFragment"
                    val fragment = isFragmentExist(tag)
                    launchFragment(fragment ?: MovieFragment(), tag)
                    true
                }
                R.id.series_menu -> {
                    val tag = "SeriesFragment"
                    val fragment = isFragmentExist(tag)
                    launchFragment(fragment ?: SeriesFragment(), tag)
                    true
                }
                R.id.favorites_menu -> {
                    val tag = "FavoritesFragment"
                    val fragment = isFragmentExist(tag)
                    launchFragment(fragment ?: FavoritesFragment(), tag)
                    true
                }
                else -> false
            }
        }
    }

    private fun isFragmentExist(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun launchBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet.root).apply {
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

    override fun launchFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, tag)
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .setPrimaryNavigationFragment(fragment)
            .commit()
    }

    override fun launchItemDetails(item: Item) {
        binding.bottomSheet.apply {
            rateView.setPushedButton(3)
            movieTitle.text = item.title
            movieRate.text = getString(R.string.rating) + item.rating
            movieDescription.text = item.description
            Picasso.get()
                .load(PrivateData.ApiConstants.IMAGES_URL + "w342" + item.posterPath)
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(posterImageView)
        }
        BottomSheetBehavior.from(binding.bottomSheet.root).state = BottomSheetBehavior.STATE_HALF_EXPANDED
        binding.bottomNavigationBarView.visibility = View.INVISIBLE
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
