package com.lefarmico.moviesfinder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.moviesfinder.data.dao.ItemHeaderDao
import com.lefarmico.moviesfinder.models.ItemHeaderModel

@Database(entities = [ItemHeaderModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): ItemHeaderDao
}
