package com.lefarmico.moviesfinder.utils

import com.lefarmico.moviesfinder.data.appEntity.*
import com.lefarmico.moviesfinder.data.appEntity.Provider
import com.lefarmico.moviesfinder.data.entity.preferences.TmdbMovieDetailsResult
import com.lefarmico.moviesfinder.data.entity.preferences.TmdbMovieResult
import com.lefarmico.moviesfinder.data.entity.preferences.TmdbProvidersResult
import com.lefarmico.moviesfinder.data.entity.preferences.credits.TmdbCast
import com.lefarmico.moviesfinder.data.entity.preferences.credits.TmdbCrew
import com.lefarmico.moviesfinder.data.entity.preferences.details.TmdbGenre

object Converter {
    fun convertApiListToDTOList(list: List<TmdbMovieResult>?): List<ItemHeaderImpl> {
        val movieList = mutableListOf<ItemHeaderImpl>()
        list?.forEach {
            movieList.add(
                ItemHeaderImpl(
                    itemId = it.id,
                    posterPath = it.posterPath,
                    title = it.title,
                    rating = it.voteAverage,
                    description = it.overview,
                    isFavorite = false,
                    yourRate = 0,
                    releaseDate = it.releaseDate
                )
            )
        }
        return movieList
    }

    fun convertApiMovieDetailsCreditsProvidersToDTOItem(
        itemHeader: ItemHeader,
        country: String,
        tmdbItem: TmdbMovieDetailsResult
    ): Movie {
        return Movie(
            itemId = tmdbItem.id,
            posterPath = tmdbItem.poster_path,
            title = tmdbItem.title,
            rating = itemHeader.rating,
            description = itemHeader.description,
            isFavorite = false,
            genres = convertGenres(tmdbItem.genres),
            yourRate = 0,
            actors = convertCast(tmdbItem.credits.tmdbCast),
            directors = convertDirectors(tmdbItem.credits.tmdbCrew),
            watchProviders = convertProviders(tmdbItem.providers, country),
            length = tmdbItem.runtime,
            photosPath = listOf(),
            releaseDate = itemHeader.releaseDate
        )
    }
    private fun convertGenres(genresList: List<TmdbGenre>): List<String> {
        val genres = mutableListOf<String>()
        genresList.forEach {
            genres.add(it.name)
        }
        return genres
    }
    private fun convertCast(tmdbCastList: List<TmdbCast>?): List<Cast> {
        val cast = mutableListOf<Cast>()
        if (tmdbCastList == null) return cast
        tmdbCastList.forEach {
            if (it.order < 10) {
                cast.add(
                    Cast(
                        name = it.name,
                        profilePath = it.profile_path,
                        character = it.character,
                        personId = it.id
                    )
                )
            }
        }
        return cast
    }
    private fun convertDirectors(castList: List<TmdbCrew>): List<Cast> {
        val cast = mutableListOf<Cast>()
        val count = if (cast.size >= 10) {
            10
        } else {
            cast.size
        }
        for (i in 0 until count) {
            cast.add(
                Cast(
                    name = castList[i].name,
                    profilePath = castList[i].profile_path,
                    character = castList[i].department,
                    personId = castList[i].id
                )
            )
        }
        return cast
    }

    private fun convertProviders(providers: TmdbProvidersResult, country: String): List<Provider> {
        val providersList = mutableListOf<Provider>()
        providers.results.getCountryProviderByName(country)?.apply {
            tmdbBuy?.forEach {
                providersList.add(
                    Provider(
                        providerType = Provider.ProviderType.BUY,
                        name = it.provider_name,
                        providerId = it.provider_id,
                        logoPath = it.logo_path,
                        displayPriority = it.display_priority
                    )
                )
            }
            tmdbFlatrate?.forEach {
                providersList.add(
                    Provider(
                        providerType = Provider.ProviderType.FLATRATE,
                        name = it.provider_name,
                        providerId = it.provider_id,
                        logoPath = it.logo_path,
                        displayPriority = it.display_priority
                    )
                )
            }
            rent?.forEach {
                providersList.add(
                    Provider(
                        providerType = Provider.ProviderType.RENT,
                        name = it.provider_name,
                        providerId = it.provider_id,
                        logoPath = it.logo_path,
                        displayPriority = it.display_priority
                    )
                )
            }
        }
        return providersList.distinctBy { it.name }
    }
}
