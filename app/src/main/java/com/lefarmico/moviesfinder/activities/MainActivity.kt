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
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.decorators.FabMenuAnimator
import com.lefarmico.moviesfinder.fragments.FavoritesFragment
import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.fragments.SeriesFragment
import com.lefarmico.moviesfinder.models.Item
import com.lefarmico.moviesfinder.view.MainView

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding

    val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentLauncher(MovieFragment(), "MovieFragment")
        launchBottomSheet()
        fabClick()

        binding.bottomNavigationBarView
            .setOnNavigationItemSelectedListener(setMenuChangeListener())
        // TODO : Обработать поворот экрана
    }

    private fun launchBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet.root).apply {
            skipCollapsed = true
            state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    override val fragments: Map<String, Fragment> = mutableMapOf()

    private fun fabClick() {
        FabMenuAnimator(
            binding.fabMenu.fabMovies,
            binding.fabMenu.fabSeries,
            binding.fabMenu.fabShows,
        ).apply {
            setAnimator(200)
            binding.fab.setOnClickListener {
                onMenuClick()
            }
        }
    }

    private fun fragmentLauncher(startFragment: Fragment, startFragmentTag: String) {
        if (supportFragmentManager.primaryNavigationFragment == null) {
            launchStartFragment(startFragment, startFragmentTag)
        } else {
            launchCurrentFragment()
        }
    }

    private fun launchStartFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, tag)
            .addToBackStack(null)
            .setPrimaryNavigationFragment(fragment)
            .commit()
    }

    private fun launchCurrentFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, supportFragmentManager.primaryNavigationFragment!!)
    }

    private fun setMenuChangeListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movies_menu -> {
                    val tag = "MovieFragment"
                    val fragment = isFragmentExist(tag)
                    changeFragment(fragment ?: MovieFragment(), tag)
                    true
                }
                R.id.series_menu -> {
                    val tag = "SeriesFragment"
                    val fragment = isFragmentExist(tag)
                    changeFragment(fragment ?: SeriesFragment(), tag)
                    true
                }
                R.id.favorites_menu -> {
                    val tag = "FavoritesFragment"
                    val fragment = isFragmentExist(tag)
                    changeFragment(fragment ?: FavoritesFragment(), tag)
                    true
                }
                else -> false
            }
        }
    }

    private fun isFragmentExist(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, tag)
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .setPrimaryNavigationFragment(fragment)
            .commit()
    }
    override fun launchFragment(fragment: Fragment, tag: String) {
        TODO("Not yet implemented")
    }

    override fun launchItemDetails(item: Item) {
        binding.bottomSheet.apply {
            rateView.setPushedButton(3)
            posterImageView.setImageResource(item.posterId)
            movieTitle.text = item.title
        }
        BottomSheetBehavior.from(binding.bottomSheet.root).apply {
            skipCollapsed = true
            state = BottomSheetBehavior.STATE_HALF_EXPANDED

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
        binding.bottomNavigationBarView.visibility = View.INVISIBLE
    }

    override fun launchNavigationComponent() {
        TODO("Not yet implemented")
    }

    override fun showError() {
        TODO("Not yet implemented")
    }
}
