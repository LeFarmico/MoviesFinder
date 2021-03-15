package com.lefarmico.moviesfinder.di

import com.lefarmico.moviesfinder.data.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModel {

    @Provides
    @Singleton
    fun provideRepository(): MainRepository = MainRepository()
}
