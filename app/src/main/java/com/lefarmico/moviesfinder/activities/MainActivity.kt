package com.lefarmico.moviesfinder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.Item
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.fragments.DetailsFragment
import com.lefarmico.moviesfinder.fragments.FavoritesFragment
import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.fragments.SeriesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        launchHomeFragment()

        binding.bottomNavigationBarView.setOnNavigationItemSelectedListener {
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
    // TODO: Find fab_menu items
//    private fun onFloatingActionButtonClick(){
//        val showAnimator = ValueAnimator.ofFloat(0f,-200f)
//        val hideAnimator = ValueAnimator.ofFloat(-200f,0f)
//        val showAlphaAnimator = ValueAnimator.ofFloat(0f,1f)
//        val hideAlphaAnimator = ValueAnimator.ofFloat(1f,0f)
//
//        val fabButtonMoveAnim = ValueAnimator.AnimatorUpdateListener {
//            binding.fab_movies.translationX = it.animatedValue as Float
//            binding.fab_series.translationX = it.animatedValue as Float
//            binding.fab_series.translationY = it.animatedValue as Float
//            binding.fab_shows.translationY = it.animatedValue as Float
//        }
//        val fabButtonAlphaAnimation = ValueAnimator.AnimatorUpdateListener {
//            fab_movies.alpha = it.animatedValue as Float
//            fab_series.alpha = it.animatedValue as Float
//            fab_shows.alpha = it.animatedValue as Float
//        }
//
//        showAnimator.addUpdateListener(fabButtonMoveAnim)
//        hideAnimator.addUpdateListener(fabButtonMoveAnim)
//        showAlphaAnimator.addUpdateListener (fabButtonAlphaAnimation)
//        hideAlphaAnimator.addUpdateListener (fabButtonAlphaAnimation)
//
//        fab.setOnClickListener {
//            if(fab_movies.alpha == 0f){
//                showAlphaAnimator.start()
//                showAnimator.start()
//            }else if(fab_movies.alpha == 1f){
//                hideAnimator.start()
//                hideAlphaAnimator.start()
//            }
//
//        }
//    }
    private fun launchHomeFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, MovieFragment(), "MovieFragment")
            .addToBackStack(null)
            .commit()
    }
    private fun isFragmentExist(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    fun launchDetailsFragment(movie: Item) {
        val bundle = Bundle()
        bundle.putSerializable("movie", movie)
        val fragment = DetailsFragment()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }
}
