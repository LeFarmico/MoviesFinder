package com.lefarmico.moviesfinder.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.moviesfinder.data.dataBase.dao.SavedMoviesDao
import com.lefarmico.moviesfinder.data.entity.*

@Database(
    entities = [
        MovieBriefData::class,
        MovieDetailedData::class,
        SearchRequestData::class
    ],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): SavedMoviesDao
}
