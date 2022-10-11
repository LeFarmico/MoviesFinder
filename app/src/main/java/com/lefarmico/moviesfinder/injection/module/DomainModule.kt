package com.lefarmico.moviesfinder.injection.module

import com.lefarmico.moviesfinder.data.manager.Interactor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideInteractor() = Interactor()
}
