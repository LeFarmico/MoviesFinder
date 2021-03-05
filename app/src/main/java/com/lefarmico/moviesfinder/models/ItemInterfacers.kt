
package com.lefarmico.moviesfinder.models

import java.io.Serializable

interface ItemHeader : Serializable {
    val id: Int
    val posterPath: String
    val title: String
    var isFavorite: Boolean
    val rating: Double
    val description: String
    val yourRate: Int
    val releaseDate: String
}
interface MovieItem : Serializable, ItemHeader {
    override val id: Int
    override val posterPath: String
    override val title: String
    override var isFavorite: Boolean
    override val rating: Double
    override val description: String
    override val yourRate: Int
    override val releaseDate: String
    val genres: List<String>
    val actors: List<CastModel>
    val directors: List<CastModel>
    val watchProviders: List<ProviderModel>
    val length: Int
    val photosPath: List<String>
}
interface ShowItem : Serializable, ItemHeader {
    override val id: Int
    override val posterPath: String
    override val title: String
    override var isFavorite: Boolean
    override val rating: Double
    override val description: String
    override val yourRate: Int
    override val releaseDate: String
    val genres: List<String>
    val lastAirDate: String
    val numberOfEpisodes: Int
    val numberOfSeasons: Int
    val seasons: List<SeasonModel>
    val lastEpisode: EpisodeModel
}
