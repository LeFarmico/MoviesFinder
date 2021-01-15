package com.lefarmico.moviesfinder.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.fargments.MovieFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolsBar()
        launchHomeFragment()
    }

    private fun initToolsBar() {
        binding.topBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fav -> {
                    Toast.makeText(this, "Add to favorite", Toast.LENGTH_SHORT).show()
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
            .add(R.id.fragment_placeholder, MovieFragment())
            .addToBackStack(null)
            .commit()
    }

//    fun launchDetailsFragment(movie: MovieItem) {
//        val bundle = Bundle()
//        bundle.putParcelable("movie", movie)
//        val fragment = DetailsFragment()
//        fragment.arguments = bundle
//
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fragment_placeholder, fragment)
//            .addToBackStack(null)
//            .commit()
//    }
}
