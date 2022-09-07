package com.lefarmico.moviesfinder.injection.module

import com.lefarmico.moviesfinder.data.manager.MainRepository
import com.lefarmico.moviesfinder.data.manager.useCase.DeleteMovieDetailedFromDBUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.SaveMovieDetailedToDBUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//class UseCaseDbModule {
//
//    @Provides
//    @Singleton
//    fun provideSaveMovieDetailedToDBUseCase(mainRepository: MainRepository) =
//        SaveMovieDetailedToDBUseCase(mainRepository::saveMovieDetailed)
//
//    @Provides
//    @Singleton
//    fun provideDeleteMovieDetailedFromDBUseCase(mainRepository: MainRepository) =
//        DeleteMovieDetailedFromDBUseCase(mainRepository::deleteMovieDetailed)
//}
