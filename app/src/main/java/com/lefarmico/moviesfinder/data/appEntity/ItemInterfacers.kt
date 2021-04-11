
package com.lefarmico.moviesfinder.data.appEntity

import java.io.Serializable

interface ItemHeader : Serializable {
    val id: Int
    val itemId: Int
    val posterPath: String
    val title: String
    var isFavorite: Boolean
    val rating: Double
    val description: String
    val yourRate: Int
    val releaseDate: String
}
interface MovieItem : Serializable, ItemHeader {
    val genres: List<String>
    val actors: List<Cast>
    val directors: List<Cast>
    val watchProviders: List<Provider>
    val length: Int
    val photosPath: List<String>
}
interface ShowItem : Serializable, ItemHeader {
    val genres: List<String>
    val lastAirDate: String
    val numberOfEpisodes: Int
    val numberOfSeasons: Int
    val seasons: List<Season>
    val lastEpisode: Episode
}
