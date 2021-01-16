package com.lefarmico.moviesfinder

import com.lefarmico.moviesfinder.data.Header
import com.lefarmico.moviesfinder.data.Item
import com.lefarmico.moviesfinder.data.MovieItem
import kotlin.random.Random

class DataFactory {

    private val categoryTitle = listOf("Hottest", "Top Views", "Favorite", "Hits", "Newest", "Your favorite")
    private val random = Random(123456789)

    private val moviesItems: List<MovieItem> = listOf<MovieItem>(
        MovieItem(R.drawable.avengers_endgame, "Avengers Endgame"),
        MovieItem(R.drawable.jaws, "Jaws"),
        MovieItem(R.drawable.big_lebovski, "Big Lebovski"),
        MovieItem(R.drawable.texet, "Tenet"),
        MovieItem(R.drawable.parasite, "Parasite"),
        MovieItem(R.drawable.spider_man_3, "Spider Man 3"),
        MovieItem(R.drawable.shawshank_redemption, "Shawshank Redemption"),
        MovieItem(R.drawable.rocky, "Rocky"),
        MovieItem(R.drawable.pulp_fiction, "Pulp Fiction"),
        MovieItem(R.drawable.matrix, "Matrix"),
        MovieItem(R.drawable.lord_of_the_rings, "Lord Of The Rings"),
        MovieItem(R.drawable.james_bond, "james Bond"),
        MovieItem(R.drawable.blade_runner, "Blade Runner"),
    )
    fun getRandomMovies(movieCount: Int): List<Item> {
        val moviesList = mutableListOf<Item>()
        repeat(movieCount) {
            moviesList.add(getRandomMovie())
        }
        return moviesList
    }

    private fun getHeader(): Header = Header(getRandomCategory())
    private fun getRandomMovie(): MovieItem = moviesItems[random.nextInt(0, moviesItems.size - 1)]
    fun getRandomCategory(): String = categoryTitle[random.nextInt(0, categoryTitle.size - 1)]
}
