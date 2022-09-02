package com.lefarmico.moviesfinder.utils.mapper

import android.util.Log
import com.lefarmico.moviesfinder.data.entity.*
import com.lefarmico.remote_module.tmdbEntity.preferences.TmdbMovieDetailsResult
import com.lefarmico.remote_module.tmdbEntity.preferences.TmdbMovieResult
import com.lefarmico.remote_module.tmdbEntity.preferences.TmdbProvidersResult
import com.lefarmico.remote_module.tmdbEntity.preferences.credits.TmdbCast
import com.lefarmico.remote_module.tmdbEntity.preferences.credits.TmdbCrew
import com.lefarmico.remote_module.tmdbEntity.preferences.details.TmdbGenre
import java.lang.NullPointerException

// TODO: change all mappers
object Converter {
    fun convertApiListToDTOList(list: List<TmdbMovieResult?>?): List<MovieBriefData> {
        val movieList = mutableListOf<MovieBriefData>()
        list?.forEach {
            if (it?.id == null) {
                throw NullPointerException("id parameter is Null")
            }
            movieList.add(
                MovieBriefData(
                    itemId = it.id,
                    posterPath = it.posterPath,
                    title = it.title,
                    rating = it.voteAverage,
                    description = it.overview,
                    isWatchlist = false,
                    yourRate = 0,
                    releaseDate = it.releaseDate
                )
            )
        }
        return movieList
    }

    fun convertApiToDTO(item: TmdbMovieResult): MovieBriefData? {
        return try {
            MovieBriefData(
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
        movieBriefData: MovieBriefData,
        country: String,
        tmdbItem: TmdbMovieDetailsResult
    ): MovieData {
        return MovieData(
            id = movieBriefData.id,
            itemId = tmdbItem.id,
            posterPath = tmdbItem.poster_path ?: "",
            title = tmdbItem.title,
            rating = tmdbItem.vote_average,
            description = tmdbItem.overview,
            isWatchlist = movieBriefData.isWatchlist,
            genres = convertGenres(tmdbItem.genres),
            yourRate = 0,
            actors = convertCast(tmdbItem.credits.tmdbCast),
            directors = convertDirectors(tmdbItem.credits.tmdbCrew),
            watchMovieProviderData = convertProviders(tmdbItem.providers, country),
            length = tmdbItem.runtime,
            photosPath = listOf(),
            releaseDate = tmdbItem.release_date
        )
    }
    private fun convertGenres(genresList: List<TmdbGenre>): List<String> {
        val genres = mutableListOf<String>()
        genresList.forEach {
            genres.add(it.name)
        }
        return genres
    }
    private fun convertCast(tmdbCastList: List<TmdbCast>?): List<MovieCastData> {
        val movieCastData = mutableListOf<MovieCastData>()
        if (tmdbCastList == null) return movieCastData
        tmdbCastList.forEach {
            if (it.order < 10) {
                movieCastData.add(
                    MovieCastData(
                        name = it.name,
                        profilePath = it.profile_path,
                        character = it.character,
                        personId = it.id
                    )
                )
            }
        }
        return movieCastData
    }
    private fun convertDirectors(tmdbCrewList: List<TmdbCrew>): List<MovieCastData> {
        val movieCastData = mutableListOf<MovieCastData>()
        if (tmdbCrewList == null) return movieCastData
        val count = if (tmdbCrewList.size >= 10) {
            10
        } else {
            movieCastData.size
        }
        for (i in 0 until count) {
            movieCastData.add(
                MovieCastData(
                    name = tmdbCrewList[i].name,
                    profilePath = tmdbCrewList[i].profile_path,
                    character = tmdbCrewList[i].department,
                    personId = tmdbCrewList[i].id
                )
            )
        }
        return movieCastData
    }

    private fun convertProviders(providers: TmdbProvidersResult, country: String): List<MovieProviderData> {
        val providersList = mutableListOf<MovieProviderData>()
        providers.results.getCountryProviderByName(country)?.apply {
            tmdbBuy?.forEach {
                providersList.add(
                    MovieProviderData(
                        providerType = MovieProviderData.ProviderType.BUY,
                        name = it.provider_name,
                        providerId = it.provider_id,
                        logoPath = it.logo_path,
                        displayPriority = it.display_priority
                    )
                )
            }
            tmdbFlatrate?.forEach {
                providersList.add(
                    MovieProviderData(
                        providerType = MovieProviderData.ProviderType.FLATRATE,
                        name = it.provider_name,
                        providerId = it.provider_id,
                        logoPath = it.logo_path,
                        displayPriority = it.display_priority
                    )
                )
            }
            rent?.forEach {
                providersList.add(
                    MovieProviderData(
                        providerType = MovieProviderData.ProviderType.RENT,
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
