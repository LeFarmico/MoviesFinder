package com.lefarmico.moviesfinder

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.category_line_bar_horizontal.view.*

class MainActivity : AppCompatActivity() {
    private var actionMode: ActionMode? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        top_bar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.fav -> {
                    Toast.makeText(this, "Add to favorite", Toast.LENGTH_SHORT).show()
                    true
                }
            else -> false
            }
        }


        val movieList = mutableListOf<ImageButton>()

        top_movie.category_name.text = "TOP MOVIE"
        hottest_movie.category_name.text = "HOTTEST MOVIE"
        recommendations.category_name.text = "RECCOMENDATIONS"
        last_seen.category_name.text = "LAST_SEEN"
        
        movieList.add(top_movie.movie_1_1)
        movieList.add(top_movie.movie_1_2)
        movieList.add(top_movie.movie_1_3)
        movieList.add(top_movie.movie_1_4)

        top_movie.movie_1_1.setImageResource(R.drawable.jocker)
        top_movie.movie_1_2.setImageResource(R.drawable.avengers_endgame)
        top_movie.movie_1_3.setImageResource(R.drawable.blade_runner)
        top_movie.movie_1_4.setImageResource(R.drawable.doctor_sleep)

        movieList.add(hottest_movie.movie_1_1)
        movieList.add(hottest_movie.movie_1_2)
        movieList.add(hottest_movie.movie_1_3)
        movieList.add(hottest_movie.movie_1_4)

        hottest_movie.movie_1_1.setImageResource(R.drawable.jocker)
        hottest_movie.movie_1_2.setImageResource(R.drawable.avengers_endgame)
        hottest_movie.movie_1_3.setImageResource(R.drawable.blade_runner)
        hottest_movie.movie_1_4.setImageResource(R.drawable.doctor_sleep)

        movieList.add(recommendations.movie_1_1)
        movieList.add(recommendations.movie_1_2)
        movieList.add(recommendations.movie_1_3)
        movieList.add(recommendations.movie_1_4)

        recommendations.movie_1_1.setImageResource(R.drawable.jocker)
        recommendations.movie_1_2.setImageResource(R.drawable.avengers_endgame)
        recommendations.movie_1_3.setImageResource(R.drawable.blade_runner)
        recommendations.movie_1_4.setImageResource(R.drawable.doctor_sleep)

        movieList.add(last_seen.movie_1_1)
        movieList.add(last_seen.movie_1_2)
        movieList.add(last_seen.movie_1_3)
        movieList.add(last_seen.movie_1_4)

        last_seen.movie_1_1.setImageResource(R.drawable.jocker)
        last_seen.movie_1_2.setImageResource(R.drawable.avengers_endgame)
        last_seen.movie_1_3.setImageResource(R.drawable.blade_runner)
        last_seen.movie_1_4.setImageResource(R.drawable.doctor_sleep)


        for (i in 0 until movieList.size) {
            movieList[i].setOnClickListener {
                Toast.makeText(this, "You push on the movie", Toast.LENGTH_SHORT).show()

                actionMode = startActionMode(object : ActionMode.Callback {
                    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        val inflater = mode?.menuInflater
                        inflater?.inflate(R.menu.top_app_bar, menu)
                        mode?.title = "Select option"
                        return true
                    }

                    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        return false
                    }

                    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                        return when (item?.itemId) {
                            R.id.fav -> {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Add to favorite",
                                    Toast.LENGTH_SHORT
                                ).show()
                                true
                            }
                            R.id.search -> {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Search movie",
                                    Toast.LENGTH_SHORT
                                ).show()
                                true
                            }
                            R.id.more -> {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Open menu",
                                    Toast.LENGTH_SHORT
                                ).show()
                                true
                            }
                            else -> false
                        }
                    }
                    override fun onDestroyActionMode(mode: ActionMode?) {}
                })
            }
        }
    }
}