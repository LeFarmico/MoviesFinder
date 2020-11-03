package com.lefarmico.moviesfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_tabs.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieList = mutableListOf<ImageButton>()


        movieList.add(movie_1_1)
        movieList.add(movie_1_2)
        movieList.add(movie_1_3)
        movieList.add(movie_1_4)
        movieList.add(movie_2_1)
        movieList.add(movie_2_2)
        movieList.add(movie_2_3)
        movieList.add(movie_2_4)
        movieList.add(movie_3_1)
        movieList.add(movie_3_2)
        movieList.add(movie_3_3)
        movieList.add(movie_3_4)
        movieList.add(movie_4_1)
        movieList.add(movie_4_2)
        movieList.add(movie_4_3)
        movieList.add(movie_4_4)
        movie_1_1.setImageResource(R.drawable.jocker)
        movie_1_2.setImageResource(R.drawable.avengers_endgame)
        movie_1_3.setImageResource(R.drawable.blade_runner)
        movie_1_4.setImageResource(R.drawable.doctor_sleep)
        movie_2_1.setImageResource(R.drawable.good_fellas)
        movie_2_2.setImageResource(R.drawable.james_bond)
        movie_2_3.setImageResource(R.drawable.lord_of_the_rings)
        movie_2_4.setImageResource(R.drawable.matrix)
        movie_3_1.setImageResource(R.drawable.pulp_fiction)
        movie_3_2.setImageResource(R.drawable.rocky)
        movie_3_3.setImageResource(R.drawable.shawshank_redemption)
        movie_3_4.setImageResource(R.drawable.spider_man_3)
        movie_4_1.setImageResource(R.drawable.texet)
        movie_4_2.setImageResource(R.drawable.jaws)
        movie_4_3.setImageResource(R.drawable.big_lebovski)
        movie_4_4.setImageResource(R.drawable.parasite)

        for (i in 0 until movieList.size){
        movieList[i].setOnClickListener { Toast.makeText(this, "You push on the movie", Toast.LENGTH_SHORT).show() }
        }

        movies_button.setOnClickListener { Toast.makeText(this, "choose your movie", Toast.LENGTH_SHORT).show() }
        series_button.setOnClickListener { Toast.makeText(this, "choose your series", Toast.LENGTH_SHORT).show() }
        shows_button.setOnClickListener { Toast.makeText(this, "choose your shows", Toast.LENGTH_SHORT).show() }
        genre.setOnClickListener { Toast.makeText(this, "search movie by genre", Toast.LENGTH_SHORT).show() }
        trends.setOnClickListener { Toast.makeText(this, "search movie by trends", Toast.LENGTH_SHORT).show() }
        recommendations.setOnClickListener { Toast.makeText(this, "look at you recommendations", Toast.LENGTH_SHORT).show() }
        top_seen.setOnClickListener { Toast.makeText(this, "top seen", Toast.LENGTH_SHORT).show() }
        collections.setOnClickListener { Toast.makeText(this, "user collections", Toast.LENGTH_SHORT).show() }
    }
}