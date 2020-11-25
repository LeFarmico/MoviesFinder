package com.lefarmico.moviesfinder

import com.lefarmico.moviesfinder.groupie.MovieContainerItem
import com.lefarmico.moviesfinder.groupie.MovieContent
import com.lefarmico.moviesfinder.groupie.MovieItem
import com.xwray.groupie.kotlinandroidextensions.Item

class GetMovieContainers() {
    fun getPopularMovies(): Item{
        return MovieContainerItem(
            "Popular Movies",
            ::onItemClick,
            listOf<Item>(
                MovieItem(MovieContent(R.drawable.big_lebovski,"Большой Лебовски")),
                MovieItem(MovieContent(R.drawable.big_lebovski,"Большой Лебовски")),
                MovieItem(MovieContent(R.drawable.big_lebovski,"Большой Лебовски")),
                MovieItem(MovieContent(R.drawable.big_lebovski,"Большой Лебовски")),
                MovieItem(MovieContent(R.drawable.big_lebovski,"Большой Лебовски")),
                MovieItem(MovieContent(R.drawable.big_lebovski,"Большой Лебовски")),
                MovieItem(MovieContent(R.drawable.big_lebovski,"Большой Лебовски")),
                MovieItem(MovieContent(R.drawable.big_lebovski,"Большой Лебовски")),
                MovieItem(MovieContent(R.drawable.big_lebovski,"Большой Лебовски")),
            )
        )
    }

    private fun onItemClick(title: String){}
}