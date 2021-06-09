package com.lefarmico.moviesfinder.data

import com.lefarmico.moviesfinder.data.appEntity.*
import com.lefarmico.moviesfinder.data.dao.ItemDao
import com.lefarmico.moviesfinder.providers.CategoryProvider
import io.reactivex.rxjava3.core.Single

class MainRepository(private val itemDao: ItemDao) {

    fun putItemHeadersToDb(headerList: List<Header>) = itemDao.insertAll(headerList)

    fun putMovieToDb(movie: Movie) = itemDao.insertMovie(movie)

    fun putCategoryDd(categoryDb: CategoryDb) = itemDao.insertCategoryDb(categoryDb)

    fun putMoviesByCategoryDB(categoryDb: CategoryDb, headerList: List<Header>) {
        headerList.forEach { header ->
            itemDao.insertMovieByCategory(
                MoviesByCategoryDb(
                    movieId = header.itemId, categoryType = categoryDb.categoryName
                )
            )
        }
    }

    fun getCategoryFromDB(categoryType: CategoryProvider.Category): Single<List<Header>> =
        itemDao.getCategory(categoryType)

    fun updateItemHeader(itemHeader: ItemHeader) {
        itemDao.updateMovieDetails(itemHeader as Header)
    }

    fun deleteMovieFromDB(movie: Movie) {
        itemDao.deleteMovie(movie)
    }

    fun putSearchRequestToDB(requestText: String) {
        itemDao.putSearchHeader(
            SearchRequestDb(textRequest = requestText)
        )
    }

    fun getSearchRequests(): Single<List<String>> = itemDao.getLastSearchRequests()
}
