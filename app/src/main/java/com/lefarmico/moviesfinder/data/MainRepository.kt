package com.lefarmico.moviesfinder.data

import com.lefarmico.moviesfinder.data.dao.ItemHeaderDao
import com.lefarmico.moviesfinder.data.entity.appEntity.Category
import com.lefarmico.moviesfinder.data.entity.appEntity.ItemHeaderImpl
import java.util.concurrent.Executors

class MainRepository(private val itemHeaderDao: ItemHeaderDao) {

    private val listMovieCategories = mutableListOf<Category>()
    var progressBar: Boolean = false

    fun putCategory(category: Category) {
        listMovieCategories.add(category)
    }
    fun clearCategories() {
        listMovieCategories.clear()
    }
    fun returnMovieCategories() = listMovieCategories

    fun putToDb(itemList: List<ItemHeaderImpl>) {
        Executors.newSingleThreadExecutor().execute {
            itemHeaderDao.insertAll(itemList)
        }
    }
    fun getAllFromDB(): List<ItemHeaderImpl> {
        return itemHeaderDao.getCachedItemHeaders()
    }
}
