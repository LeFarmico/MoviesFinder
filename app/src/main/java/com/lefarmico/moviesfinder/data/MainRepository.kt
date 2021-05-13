package com.lefarmico.moviesfinder.data

import com.lefarmico.moviesfinder.data.appEntity.*
import com.lefarmico.moviesfinder.data.dao.ItemDao
import com.lefarmico.moviesfinder.providers.CategoryProvider

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

    fun getCategoryFromDB(categoryType: CategoryProvider.Category): Category {
        return Category(
            categoryType.getResource(),
            categoryType,
            itemDao.getCategory(categoryType)
        )
    }
    fun getAllFromDB(): List<ItemHeaderImpl> = itemDao.getCachedItemHeaders()

    fun updateItemHeader(itemHeaderImpl: ItemHeaderImpl) {
        itemDao.updateMovieDetails(itemHeaderImpl)
    }
}
