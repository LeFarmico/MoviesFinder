package com.lefarmico.moviesfinder.injection.module

import com.lefarmico.moviesfinder.data.manager.MovieDetailedRepository
import com.lefarmico.moviesfinder.data.manager.useCase.DeleteMovieDetailedFromDBUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.GetWatchListMovieDetailedFromDBUseCase
import com.lefarmico.moviesfinder.data.manager.useCase.SaveMovieDetailedToDBUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseDbModule {

    @Provides
    @Singleton
    fun provideSaveMovieDetailedToDBUseCase(movieDetailedRepository: MovieDetailedRepository) =
        SaveMovieDetailedToDBUseCase(movieDetailedRepository::saveMovieDetailed)

    @Provides
    @Singleton
    fun provideDeleteMovieDetailedFromDBUseCase(movieDetailedRepository: MovieDetailedRepository) =
        DeleteMovieDetailedFromDBUseCase(movieDetailedRepository::deleteMovieDetailed)

    @Provides
    @Singleton
    fun provideGetWatchListMovieFromDBUseCase(movieDetailedRepository: MovieDetailedRepository) =
        GetWatchListMovieDetailedFromDBUseCase(movieDetailedRepository::getWatchListMovieDetailed)
}
