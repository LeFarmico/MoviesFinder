package com.lefarmico.moviesfinder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.moviesfinder.data.dao.ItemDao
import com.lefarmico.moviesfinder.data.entity.*

@Database(
    entities = [
        MovieBriefData::class,
        MovieData::class,
        MovieIdByCategoryData::class,
        SearchRequestData::class
    ],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
