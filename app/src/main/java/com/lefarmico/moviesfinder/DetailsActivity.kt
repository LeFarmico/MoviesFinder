package com.lefarmico.moviesfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movieItem = intent.extras?.get("movieItem") as MovieItem
        if(movieItem.poster == -1)
            MovieItem(R.drawable.ic_launcher_foreground, "Нет такого фильма")
        details_toolbar.title = movieItem.title
        details_poster.setImageResource(movieItem.poster)
    }
}