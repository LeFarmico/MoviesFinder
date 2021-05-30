package com.lefarmico.moviesfinder.data

import com.lefarmico.moviesfinder.data.appEntity.CategoryDb
import com.lefarmico.moviesfinder.data.appEntity.ItemHeaderImpl
import com.lefarmico.moviesfinder.data.appEntity.Movie
import com.lefarmico.moviesfinder.data.appEntity.MoviesByCategoryDb
import com.lefarmico.moviesfinder.data.dao.ItemDao
import com.lefarmico.moviesfinder.providers.CategoryProvider
import io.reactivex.rxjava3.core.Single

class MainRepository(private val itemDao: ItemDao) {

    fun putItemHeadersToDb(itemList: List<ItemHeaderImpl>) = itemDao.insertAll(itemList)

    fun putMovieToDb(movie: Movie) = itemDao.insertMovie(movie)

    fun putCategoryDd(categoryDb: CategoryDb) = itemDao.insertCategoryDb(categoryDb)

    fun putMoviesByCategoryDB(categoryDb: CategoryDb, itemHeaderImplList: List<ItemHeaderImpl>) {
        for (i in itemHeaderImplList.indices) {
            itemDao.insertMovieByCategory(
                MoviesByCategoryDb(
                    movieId = itemHeaderImplList[i].itemId, categoryType = categoryDb.categoryName
                )
            )
        }
    }

    fun getCategoryFromDB(categoryType: CategoryProvider.Category): Single<List<ItemHeaderImpl>> =
        itemDao.getCategory(categoryType)

    fun updateItemHeader(itemHeaderImpl: ItemHeaderImpl) {
        itemDao.updateMovieDetails(itemHeaderImpl)
    }

    fun deleteMovieFromDB(movie: Movie) {
        itemDao.deleteMovie(movie)
    }
}
