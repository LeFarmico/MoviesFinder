package com.lefarmico.moviesfinder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lefarmico.moviesfinder.data.appEntity.*
import com.lefarmico.moviesfinder.data.dao.ItemHeaderDao

@Database(
    entities = [
        ItemHeaderImpl::class,
        Cast::class,
        Provider::class,
        GenresDb::class,
        PhotosDb::class
    ],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemHeaderDao(): ItemHeaderDao
}
