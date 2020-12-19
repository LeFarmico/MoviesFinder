package com.lefarmico.moviesfinder

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.lefarmico.moviesfinder.adapterDelegates.ContainerAdapter
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.fab_menu.*
import kotlinx.android.synthetic.main.parent_recycler.*
import kotlinx.android.synthetic.main.parent_recycler.view.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Запуск activity_main
        setContentView(R.layout.activity_main)
        //Запуск ToolsBar
        initToolsBar()
        //Запуск адаптивного fab
        onFloatingActionButtonClick()
        //Запуск HomeFragment
        launchHomeFragment()

        setFabAboveBottomNavigationBar() //пока не работает
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.show()
        Log.i("MainActivity", "onResume()")
    }
    override fun onPause() {
        super.onPause()
        supportActionBar?.hide()
        Log.i("MainActivity", "onPause()")
    }

    private fun initToolsBar(){
        top_bar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.fav -> {
                    Toast.makeText(this, "Add to favorite", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
    private fun onFloatingActionButtonClick(){
        val showAnimator = ValueAnimator.ofFloat(0f,-200f)
        val hideAnimator = ValueAnimator.ofFloat(-200f,0f)
        val showAlphaAnimator = ValueAnimator.ofFloat(0f,1f)
        val hideAlphaAnimator = ValueAnimator.ofFloat(1f,0f)

        val fabButtonMoveAnim = ValueAnimator.AnimatorUpdateListener {
            fab_movies.translationX = it.animatedValue as Float
            fab_series.translationX = it.animatedValue as Float
            fab_series.translationY = it.animatedValue as Float
            fab_shows.translationY = it.animatedValue as Float
        }
        val fabButtonAlphaAnimation = ValueAnimator.AnimatorUpdateListener {
            fab_movies.alpha = it.animatedValue as Float
            fab_series.alpha = it.animatedValue as Float
            fab_shows.alpha = it.animatedValue as Float
        }

        showAnimator.addUpdateListener(fabButtonMoveAnim)
        hideAnimator.addUpdateListener(fabButtonMoveAnim)
        showAlphaAnimator.addUpdateListener (fabButtonAlphaAnimation)
        hideAlphaAnimator.addUpdateListener (fabButtonAlphaAnimation)

        fab.setOnClickListener {
            if(fab_movies.alpha == 0f){
                showAlphaAnimator.start()
                showAnimator.start()
            }else if(fab_movies.alpha == 1f){
                hideAnimator.start()
                hideAlphaAnimator.start()
            }

        }
    }
    private fun launchHomeFragment(){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
    }
    private fun setFabAboveBottomNavigationBar(){
    }

    fun launchDetailsFragment(movie: MovieItem){
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }
}
