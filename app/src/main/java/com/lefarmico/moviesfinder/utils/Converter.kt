package com.lefarmico.moviesfinder.utils

import android.util.Log
import com.lefarmico.moviesfinder.data.appEntity.*
import com.lefarmico.remote_module.tmdbEntity.preferences.TmdbMovieDetailsResult
import com.lefarmico.remote_module.tmdbEntity.preferences.TmdbMovieResult
import com.lefarmico.remote_module.tmdbEntity.preferences.TmdbProvidersResult
import com.lefarmico.remote_module.tmdbEntity.preferences.credits.TmdbCast
import com.lefarmico.remote_module.tmdbEntity.preferences.credits.TmdbCrew
import com.lefarmico.remote_module.tmdbEntity.preferences.details.TmdbGenre
import java.lang.NullPointerException

object Converter {
    fun convertApiListToDTOList(list: List<TmdbMovieResult?>?): List<Header> {
        val movieList = mutableListOf<Header>()
        list?.forEach {
            if (it?.id == null) {
                throw NullPointerException("id parameter is Null")
            }
            movieList.add(
                Header(
                    itemId = it.id,
                    posterPath = it.posterPath ?: "",
                    title = it.title ?: "",
                    rating = it.voteAverage ?: 0.0,
                    description = it.overview ?: "",
                    isWatchlist = false ?: false,
                    yourRate = 0 ?: 0,
                    releaseDate = it.releaseDate ?: "00.00.0000"
                )
            )
        }
        return movieList
    }

    fun convertApiToDTO(item: TmdbMovieResult): Header? {
        return try {
            Header(
                itemId = item.id,
                posterPath = item.posterPath,
                title = item.title,
                rating = item.voteAverage,
                description = item.overview,
                isWatchlist = false,
                yourRate = 0,
                releaseDate = item.releaseDate
            )
        } catch (e: NullPointerException) {
            Log.e("Converter", "Converter cached Null from TmdbMovieResult object", e)
            null
        }
    }

    fun convertApiMovieDetailsToDTOItem(
        itemHeader: ItemHeader,
        country: String,
        tmdbItem: TmdbMovieDetailsResult
    ): Movie {
        return Movie(
            id = itemHeader.id,
            itemId = tmdbItem.id,
            posterPath = tmdbItem.poster_path ?: "",
            title = tmdbItem.title,
            rating = tmdbItem.vote_average ?: 0.0,
            description = tmdbItem.overview ?: "",
            isWatchlist = itemHeader.isWatchlist ?: false,
            genres = convertGenres(tmdbItem.genres),
            yourRate = 0,
            actors = convertCast(tmdbItem.credits.tmdbCast),
            directors = convertDirectors(tmdbItem.credits.tmdbCrew),
            watchProviders = convertProviders(tmdbItem.providers, country),
            length = tmdbItem.runtime,
            photosPath = listOf(),
            releaseDate = tmdbItem.release_date ?: "00.00.0000"
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
    private fun convertDirectors(tmdbCrewList: List<TmdbCrew>): List<Cast> {
        val cast = mutableListOf<Cast>()
        if (tmdbCrewList == null) return cast
        val count = if (tmdbCrewList.size >= 10) {
            10
        } else {
            cast.size
        }
        for (i in 0 until count) {
            cast.add(
                Cast(
                    name = tmdbCrewList[i].name,
                    profilePath = tmdbCrewList[i].profile_path,
                    character = tmdbCrewList[i].department,
                    personId = tmdbCrewList[i].id
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
