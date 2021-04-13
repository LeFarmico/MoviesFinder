package com.lefarmico.moviesfinder.data

import androidx.lifecycle.MutableLiveData
import com.lefarmico.moviesfinder.data.appEntity.*
import com.lefarmico.moviesfinder.data.dao.ItemDao
import com.lefarmico.moviesfinder.providers.CategoryProvider
import java.util.concurrent.Executors

class MainRepository(private val itemDao: ItemDao) {

    var progressBar: Boolean = false

    fun putItemHeadersToDb(itemList: List<ItemHeaderImpl>) {
        Executors.newSingleThreadExecutor().execute {
            itemDao.insertAll(itemList)
        }
    }
    fun putMovieToDb(movie: Movie) {
        Executors.newSingleThreadExecutor().execute {
            itemDao.insertMovie(movie)
        }
    }

    fun putCategoryDd(categoryDb: CategoryDb) {
        Executors.newSingleThreadExecutor().execute {
            itemDao.insertCategoryDb(categoryDb)
        }
    }

    fun putMovieByCategoryDB(categoryDb: CategoryDb, itemHeaderImpl: ItemHeaderImpl) {
        Executors.newSingleThreadExecutor().execute {
            itemDao.insertMovieByCategory(
                MoviesByCategoryDb(
                    movieId = itemHeaderImpl.itemId, categoryType = categoryDb.categoryName
                )
            )
        }
    }

    fun getCategoriesFromDB(categoryType: CategoryProvider.Category): MutableLiveData<Category> {
        val cat: MutableLiveData<Category> = MutableLiveData<Category>()
        Executors.newSingleThreadExecutor().execute {
            cat.postValue(
                Category(
                    categoryType.getResource(),
                    categoryType,
                    itemDao.getCategory(categoryType)
                )
            )
        }
        return cat
    }
    fun getAllFromDB(): List<ItemHeaderImpl> {
        return itemDao.getCachedItemHeaders()
    }
}
