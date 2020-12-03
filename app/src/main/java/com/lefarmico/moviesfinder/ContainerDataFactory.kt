package com.lefarmico.moviesfinder

import com.lefarmico.moviesfinder.adapterDelegates.container.Container
import com.lefarmico.moviesfinder.adapterDelegates.container.HeaderModel
import com.lefarmico.moviesfinder.adapterDelegates.container.ParentModel
import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem
import kotlin.random.Random


class ContainerDataFactory {

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
    fun getContainerModel(movieCount: Int, categoryCount: Int): List<Container>{
        val parentModelList = mutableListOf<Container>()
        repeat(categoryCount){
            val movieItemList = mutableListOf<MovieItem>()
            parentModelList.add(getHeader())
            repeat(movieCount){
                movieItemList.add(getRandomMovie())
            }
            parentModelList.add(ParentModel(movieItemList))
        }
        return  parentModelList
    }

    private fun getHeader(): HeaderModel = HeaderModel(getRandomCategory())
    private fun getRandomMovie(): MovieItem = moviesItems[random.nextInt(0,moviesItems.size-1)]
    private fun getRandomCategory(): String = categoryTitle[random.nextInt(0,categoryTitle.size-1)]
}