package com.lefarmico.moviesfinder.data

import android.util.Log
import com.lefarmico.moviesfinder.data.appEntity.*
import com.lefarmico.moviesfinder.data.dao.ItemHeaderDao
import java.util.concurrent.Executors

class MainRepository(private val itemHeaderDao: ItemHeaderDao) {

    private val listMovieCategories = mutableListOf<Category>()
    var progressBar: Boolean = false

    fun putCategory(category: Category) {
        listMovieCategories.add(category)
        Log.d("Repos", "${listMovieCategories.size}")
    }
    fun clearCategories() {
        listMovieCategories.clear()
    }
    fun returnMovieCategories() = listMovieCategories

    // ____________________________________________________________________
    fun putItemHeadersToDb(itemList: List<ItemHeaderImpl>) {
        Executors.newSingleThreadExecutor().execute {
            itemHeaderDao.insertAll(itemList)
        }
    }
    fun putMovieDetailsToDb(movie: Movie, itemHeader: ItemHeader) {
        Executors.newSingleThreadExecutor().execute {

            for (i in movie.actors.indices) {
                val actor = movie.actors[i]
                itemHeaderDao.insertCast(
                    Cast(
                        personId = actor.personId,
                        name = actor.name,
                        profilePath = actor.posterPath,
                        character = actor.character,
                        posterPath = actor.posterPath,
                        movieDetailsId = itemHeader.id
                    )
                )
            }
            for (i in movie.genres.indices) {
                val genre = movie.genres[i]
                itemHeaderDao.insertGenres(
                    GenresDb(
                        genreName = genre,
                        movieDetailsId = itemHeader.id
                    )
                )
            }

            for (i in movie.watchProviders.indices) {
                val provider = movie.watchProviders[i]
                itemHeaderDao.insertProvider(
                    Provider(
                        providerType = provider.providerType,
                        name = provider.name,
                        providerId = provider.providerId,
                        logoPath = provider.logoPath,
                        displayPriority = provider.displayPriority,
                        movieDetailsId = itemHeader.id
                    )
                )
            }

            for (i in movie.photosPath.indices) {
                val photoPath = movie.photosPath[i]
                itemHeaderDao.insertPhotos(
                    PhotosDb(
                        photoPath = photoPath,
                        movieDetailsId = itemHeader.id
                    )
                )
            }
        }
    }
    fun putMovieDetailsIdToItemHeader(movie: Movie, itemHeader: ItemHeader) {
        Executors.newSingleThreadExecutor().execute {
            val updatedItemHeaderImpl = ItemHeaderImpl(
                id = itemHeader.id,
                itemId = itemHeader.itemId,
                posterPath = itemHeader.posterPath,
                title = itemHeader.title,
                rating = itemHeader.rating,
                description = itemHeader.description,
                isFavorite = itemHeader.isFavorite,
                yourRate = itemHeader.yourRate,
                releaseDate = itemHeader.releaseDate
            )
            itemHeaderDao.updateMovieDetails(updatedItemHeaderImpl)
        }
    }
    fun getAllFromDB(): List<ItemHeaderImpl> {
        return itemHeaderDao.getCachedItemHeaders()
    }
}
