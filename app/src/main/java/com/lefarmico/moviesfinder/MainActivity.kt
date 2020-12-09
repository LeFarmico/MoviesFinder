package com.lefarmico.moviesfinder

import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.adapterDelegates.ContainerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fab_menu.*
import kotlinx.android.synthetic.main.parent_recycler.*
import kotlinx.android.synthetic.main.parent_recycler.view.*


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initToolsBar()
        initDelegatesRecycler()
        onFloatingActionButtonClick()
        setFabAboveBottomNavigationBar() //пока не работает
    }
    private fun initDelegatesRecycler(){
        recyclerView = recycler_parent
        recyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                RecyclerView.VERTICAL,
                false
            )
            adapter = ContainerAdapter()
            (adapter as ContainerAdapter).items = ContainerDataFactory().getContainerModel(10, 10)
        }
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
    private fun setFabAboveBottomNavigationBar(){

    }
}
