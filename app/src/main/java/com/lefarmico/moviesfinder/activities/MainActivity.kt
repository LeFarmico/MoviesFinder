package com.lefarmico.moviesfinder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.data.Item
import com.lefarmico.moviesfinder.databinding.ActivityMainBinding
import com.lefarmico.moviesfinder.fragments.DetailsFragment
import com.lefarmico.moviesfinder.fragments.MovieFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolsBar()

        launchHomeFragment()

        binding.fab.setOnClickListener {
        }
    }

    private fun initToolsBar() {
        binding.searchViewBar.setOnClickListener {
            (it as SearchView).isIconified = false
        }
        binding.searchViewBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
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
    private fun updateFragment(): MovieFragment {
        return supportFragmentManager
            .findFragmentByTag("MovieFragment") as MovieFragment
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
