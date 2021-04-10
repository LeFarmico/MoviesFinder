package com.lefarmico.moviesfinder.data

import android.util.Log
import com.lefarmico.moviesfinder.data.appEntity.Category
import com.lefarmico.moviesfinder.data.appEntity.ItemHeaderImpl
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

    fun putToDb(itemList: List<ItemHeaderImpl>) {
        Executors.newSingleThreadExecutor().execute {
            itemHeaderDao.insertAll(itemList)
        }
    }
    fun getAllFromDB(): List<ItemHeaderImpl> {
        return itemHeaderDao.getCachedItemHeaders()
    }
}
