package com.lefarmico.moviesfinder.activities

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.Item
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.fragments.DetailsFragment
import com.lefarmico.moviesfinder.fragments.FavoritesFragment
import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.fragments.SeriesFragment

class MainActivity : AppCompatActivity() {

    lateinit var navHost: NavHostFragment
    lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding
    val TAG = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentLauncher(MovieFragment(), "MovieFragment")
        onFloatingActionButtonClick()

        binding.bottomNavigationBarView
            .setOnNavigationItemSelectedListener(setMenuChangeListener())
    }

    fun launchDetailsFragment(movie: Item) {
        val bundle = Bundle()
        bundle.putSerializable("movie", movie)
        val fragment = DetailsFragment()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun onFloatingActionButtonClick() {
        val showAnimator = ValueAnimator.ofFloat(0f, -200f)
        val hideAnimator = ValueAnimator.ofFloat(-200f, 0f)
        val showAlphaAnimator = ValueAnimator.ofFloat(0f, 1f)
        val hideAlphaAnimator = ValueAnimator.ofFloat(1f, 0f)

        val fabButtonMoveAnim = ValueAnimator.AnimatorUpdateListener {
            binding.fabMenu.fabMovies.translationX = it.animatedValue as Float
            binding.fabMenu.fabSeries.translationX = it.animatedValue as Float
            binding.fabMenu.fabSeries.translationY = it.animatedValue as Float
            binding.fabMenu.fabShows.translationY = it.animatedValue as Float
        }
        val fabButtonAlphaAnimation = ValueAnimator.AnimatorUpdateListener {
            binding.fabMenu.fabMovies.alpha = it.animatedValue as Float
            binding.fabMenu.fabSeries.alpha = it.animatedValue as Float
            binding.fabMenu.fabShows.alpha = it.animatedValue as Float
        }

        showAnimator.addUpdateListener(fabButtonMoveAnim)
        hideAnimator.addUpdateListener(fabButtonMoveAnim)
        showAlphaAnimator.addUpdateListener(fabButtonAlphaAnimation)
        hideAlphaAnimator.addUpdateListener(fabButtonAlphaAnimation)

        binding.fab.setOnClickListener {
            if (binding.fabMenu.fabMovies.alpha == 0f) {
                showAlphaAnimator.start()
                showAnimator.start()
            } else if (binding.fabMenu.fabMovies.alpha == 1f) {
                hideAnimator.start()
                hideAlphaAnimator.start()
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
    private fun isFragmentExist(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)
    private fun changeFragment(fragment: Fragment, tag: String) {
        if (isFragmentExist(tag) != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, tag)
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .setPrimaryNavigationFragment(fragment)
                .commit()
            return
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, tag)
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .setPrimaryNavigationFragment(fragment)
            .commit()
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
    private fun setNavigationComponentListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener {
            navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHost.navController
            when (it.itemId) {
                R.id.movies_menu -> {
                    navController.navigate(R.id.movieFragment)
                    true
                }
                R.id.series_menu -> {
                    navController.navigate(R.id.seriesFragment)
                    true
                }
                R.id.favorites_menu -> {
                    navController.navigate(R.id.favoritesFragment)
                    true
                }
                else -> false
            }
        }
    }
}
