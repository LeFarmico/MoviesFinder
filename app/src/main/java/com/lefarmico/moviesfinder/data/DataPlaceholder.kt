package com.lefarmico.moviesfinder.data

import com.lefarmico.moviesfinder.R
import com.lefarmico.moviesfinder.models.HeaderModel
import com.lefarmico.moviesfinder.models.MovieItemModel
import kotlin.random.Random

class DataPlaceholder {

    private val random = Random(123456789)

    val headers = listOf(
        HeaderModel(R.string.action_category, 1),
        HeaderModel(R.string.top_category, 2),
        HeaderModel(R.string.most_category, 3),
        HeaderModel(R.string.hits_category, 4),
        HeaderModel(R.string.drama_category, 5),
        HeaderModel(R.string.comedy_category, 6)
    )

    val moviesItems: List<MovieItemModel> = listOf(
        MovieItemModel(R.drawable.avengers_endgame, "Avengers Endgame"),
        MovieItemModel(R.drawable.jaws, "Jaws"),
        MovieItemModel(R.drawable.big_lebovski, "Big Lebovski"),
        MovieItemModel(R.drawable.texet, "Tenet"),
        MovieItemModel(R.drawable.parasite, "Parasite"),
        MovieItemModel(R.drawable.spider_man_3, "Spider Man 3"),
        MovieItemModel(R.drawable.shawshank_redemption, "Shawshank Redemption"),
        MovieItemModel(R.drawable.rocky, "Rocky"),
        MovieItemModel(R.drawable.pulp_fiction, "Pulp Fiction"),
        MovieItemModel(R.drawable.matrix, "Matrix"),
        MovieItemModel(R.drawable.lord_of_the_rings, "Lord Of The Rings"),
        MovieItemModel(R.drawable.james_bond, "james Bond"),
        MovieItemModel(R.drawable.blade_runner, "Blade Runner"),
    )
}
