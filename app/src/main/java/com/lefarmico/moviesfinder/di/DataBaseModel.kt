package com.lefarmico.moviesfinder.di

import android.content.Context
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.data.db.DatabaseHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModel {

    @Provides
    @Singleton
    fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)

    @Provides
    @Singleton
    fun provideRepository(databaseHelper: DatabaseHelper): MainRepository = MainRepository(databaseHelper)
}
