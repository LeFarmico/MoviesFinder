package com.lefarmico.moviesfinder.data

import com.lefarmico.moviesfinder.data.dao.ItemHeaderDao
import com.lefarmico.moviesfinder.models.ItemHeaderModel
import java.util.concurrent.Executors

class MainRepository(private val itemHeaderDao: ItemHeaderDao) {

    fun putToDb(itemList: List<ItemHeaderModel>) {
        Executors.newSingleThreadExecutor().execute {
            itemHeaderDao.insertAll(itemList)
        }
    }
    fun getAllFromDB(): List<ItemHeaderModel> {
        return itemHeaderDao.getCachedItemHeaders()
    }
}
