package com.lefarmico.moviesfinder.utils.mapper

import com.lefarmico.moviesfinder.data.entity.*
import com.lefarmico.moviesfinder.data.http.response.entity.TmdbMovieDetailsResult
import com.lefarmico.moviesfinder.data.http.response.entity.TmdbMovieResult
import com.lefarmico.moviesfinder.data.http.response.entity.credits.TmdbCast
import com.lefarmico.moviesfinder.data.http.response.entity.credits.TmdbCrew
import com.lefarmico.moviesfinder.data.http.response.entity.details.TmdbGenre
import com.lefarmico.moviesfinder.data.http.response.entity.providers.*

fun TmdbMovieResult.toBriefViewData(
    isWatchList: Boolean = false,
    yourRate: Int? = null
) = MovieBriefData(
    movieId = this.id,
    posterPath = this.posterPath,
    title = this.title,
    rating = this.voteAverage,
    description = this.overview,
    isWatchlist = isWatchList,
    yourRate = yourRate,
    releaseDate = this.releaseDate
)

fun TmdbMovieDetailsResult.toDetailedViewData(
    isWatchList: Boolean = false,
    userRate: Int? = null,
    country: String = "US"
) = MovieDetailedData(
    id = this.id,
    movieId = this.id,
    posterPath = this.poster_path,
    title = this.title,
    rating = this.vote_average,
    description = this.overview,
    isWatchlist = isWatchList,
    genres = this.genres.toGenreList(),
    yourRate = userRate,
    actors = this.credits?.tmdbCast?.toCastListViewData(),
    directors = this.credits?.tmdbCrew?.toCrewListViewData(),
    watchMovieProviderData = providers?.results?.toProvidersList(country),
    length = this.runtime,
    releaseDate = this.release_date
)

fun List<TmdbGenre>.toGenreList() = this.map { it.name }

fun List<TmdbCast>.toCastListViewData() = this.map { it.toMovieCastViewData() }

fun List<TmdbCrew>.toCrewListViewData() = this.map { it.toMovieCrewViewData() }

fun TmdbCast.toMovieCastViewData() = MovieCastData(
    personId = this.id,
    name = this.name,
    profilePath = this.profile_path,
    character = this.character
)

fun TmdbCrew.toMovieCrewViewData() = MovieCrewData(
    id = this.id,
    creditId = this.credit_id,
    department = this.department,
    name = this.name,
    profilePath = profile_path
)

fun TmdbProvidersResults.toProvidersList(country: String): List<MovieProviderData> {
    val providerList = mutableSetOf<MovieProviderData>()
    this.getCountryProviderByName(country)?.apply {
        tmdbBuy?.forEach { providerList.add(it.toProviderViewData()) }
        tmdbFlatrate?.forEach { providerList.add(it.toProviderViewData()) }
        rent?.forEach { providerList.add(it.toProviderViewData()) }
    }
    return providerList.toList()
}

fun TmdbBuy.toProviderViewData() = MovieProviderData(
    name = this.provider_name,
    providerId = this.provider_id,
    logoPath = this.logo_path,
    displayPriority = this.display_priority
)

fun TmdbFlatrate.toProviderViewData() = MovieProviderData(
    name = this.provider_name,
    providerId = this.provider_id,
    logoPath = this.logo_path,
    displayPriority = this.display_priority
)

fun TmdbRent.toProviderViewData() = MovieProviderData(
    name = this.provider_name,
    providerId = this.provider_id,
    logoPath = this.logo_path,
    displayPriority = this.display_priority
)
