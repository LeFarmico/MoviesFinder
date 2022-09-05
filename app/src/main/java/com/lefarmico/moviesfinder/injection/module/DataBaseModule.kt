package com.lefarmico.moviesfinder.injection.module

import android.content.Context
import androidx.room.Room
import com.lefarmico.moviesfinder.data.dataBase.AppDatabase
import com.lefarmico.moviesfinder.data.dataBase.dao.ItemDao
import com.lefarmico.moviesfinder.data.manager.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideItemHeaderDao(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "cached_item_header"
        ).build().itemDao()

    @Provides
    @Singleton
    fun provideRepository(itemDao: ItemDao): MainRepository = MainRepository(itemDao)
}
