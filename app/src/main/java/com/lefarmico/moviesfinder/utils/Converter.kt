package com.lefarmico.moviesfinder.utils

import com.lefarmico.moviesfinder.data.entity.TmdbMovie
import com.lefarmico.moviesfinder.models.MovieItemModel

object Converter {
    fun convertApiListToDTOList(list: List<TmdbMovie>?): List<MovieItemModel> {
        val movieList = mutableListOf<MovieItemModel>()
        list?.forEach {
            movieList.add(
                MovieItemModel(
                    id = it.id,
                    posterPath = it.posterPath,
                    title = it.title,
                    genreIds = it.genreIds,
                    rating = it.voteAverage,
                    description = it.overview,
                    isFavorite = false
                )
            )
        }
        return movieList
    }
}
