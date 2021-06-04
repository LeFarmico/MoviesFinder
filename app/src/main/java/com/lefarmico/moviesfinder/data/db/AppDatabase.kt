package com.lefarmico.moviesfinder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.moviesfinder.data.appEntity.*
import com.lefarmico.moviesfinder.data.dao.ItemDao

@Database(
    entities = [
        Header::class,
        Movie::class,
        CategoryDb::class,
        MoviesByCategoryDb::class,
        SearchRequestDb::class
    ],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
