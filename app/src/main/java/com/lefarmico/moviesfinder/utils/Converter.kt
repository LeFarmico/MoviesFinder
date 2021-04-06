package com.lefarmico.moviesfinder.utils

import com.lefarmico.moviesfinder.data.entity.preferences.TmdbMovieDetailsWithCreditsAndProvidersResult
import com.lefarmico.moviesfinder.data.entity.preferences.TmdbMovieResult
import com.lefarmico.moviesfinder.data.entity.preferences.TmdbProvidersResult
import com.lefarmico.moviesfinder.data.entity.preferences.credits.Cast
import com.lefarmico.moviesfinder.data.entity.preferences.credits.Crew
import com.lefarmico.moviesfinder.data.entity.preferences.details.TmdbGenre
import com.lefarmico.moviesfinder.models.*

object Converter {
    fun convertApiListToDTOList(list: List<TmdbMovieResult>?): List<ItemHeaderModel> {
        val movieList = mutableListOf<ItemHeaderModel>()
        list?.forEach {
            movieList.add(
                ItemHeaderModel(
                    id = it.id,
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
        tmdbItem: TmdbMovieDetailsWithCreditsAndProvidersResult
    ): MovieModel {
        return MovieModel(
            id = tmdbItem.id,
            posterPath = tmdbItem.poster_path,
            title = tmdbItem.title,
            rating = itemHeader.rating,
            description = itemHeader.description,
            isFavorite = false,
            genres = convertGenres(tmdbItem.genres),
            yourRate = 0,
            actors = convertCast(tmdbItem.credits.cast),
            directors = convertDirectors(tmdbItem.credits.crew),
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
    private fun convertCast(castList: List<Cast>): List<CastModel> {
        val cast = mutableListOf<CastModel>()
        castList.forEach {
            if (it.order < 10) {
                cast.add(
                    CastModel(
                        name = it.name,
                        profilePath = it.profile_path,
                        character = it.character,
                        id = it.id
                    )
                )
            }
        }
        return cast
    }
    private fun convertDirectors(castList: List<Crew>): List<CastModel> {
        val cast = mutableListOf<CastModel>()
        val count = if (cast.size >= 10) {
            10
        } else {
            cast.size
        }
        for (i in 0 until count) {
            cast.add(
                CastModel(
                    name = castList[i].name,
                    profilePath = castList[i].profile_path,
                    character = castList[i].department,
                    id = castList[i].id
                )
            )
        }
        return cast
    }

    private fun convertProviders(providers: TmdbProvidersResult, country: String): List<ProviderModel> {
        val providersList = mutableListOf<ProviderModel>()
        providers.results.getCountryProviderByName(country)?.apply {
            buy?.forEach {
                providersList.add(
                    ProviderModel(
                        providerType = ProviderType.BUY,
                        name = it.provider_name,
                        id = it.provider_id,
                        logoPath = it.logo_path,
                        displayPriority = it.display_priority
                    )
                )
            }
            flatrate?.forEach {
                providersList.add(
                    ProviderModel(
                        providerType = ProviderType.FLATRATE,
                        name = it.provider_name,
                        id = it.provider_id,
                        logoPath = it.logo_path,
                        displayPriority = it.display_priority
                    )
                )
            }
            rent?.forEach {
                providersList.add(
                    ProviderModel(
                        providerType = ProviderType.RENT,
                        name = it.provider_name,
                        id = it.provider_id,
                        logoPath = it.logo_path,
                        displayPriority = it.display_priority
                    )
                )
            }
        }
        return providersList.distinctBy { it.name }
    }
}
