package com.lefarmico.moviesfinder.di

import android.content.Context
import androidx.room.Room
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.data.dao.ItemDao
import com.lefarmico.moviesfinder.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModel {

    @Provides
    @Singleton
    fun provideItemHeaderDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "cached_item_header"
        ).build().itemDao()

    @Provides
    @Singleton
    fun provideRepository(itemDao: ItemDao): MainRepository = MainRepository(itemDao)
}
