package com.lefarmico.moviesfinder.injection.module

import com.lefarmico.moviesfinder.data.manager.MovieBriefListRepository
import com.lefarmico.moviesfinder.data.manager.MovieDetailedRepository
import com.lefarmico.moviesfinder.data.manager.useCase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetPopularMovieBriefListCategory(
        movieBriefListRepository: MovieBriefListRepository
    ): GetPopularMovieBriefList =
        GetPopularMovieBriefList(movieBriefListRepository::getPopularMovieBriefList)

    @Singleton
    @Provides
    fun provideGetUpcomingMovieBriefListCategory(
        movieBriefListRepository: MovieBriefListRepository
    ): GetUpcomingMovieBriefList = GetUpcomingMovieBriefList(movieBriefListRepository::getUpcomingMovieBriefList)

    @Singleton
    @Provides
    fun provideGetNowPlayingMovieBriefListCategory(
        movieBriefListRepository: MovieBriefListRepository
    ): GetNowPlayingMovieBriefList = GetNowPlayingMovieBriefList(movieBriefListRepository::getNowPlayingMovieBriefList)

    @Singleton
    @Provides
    fun provideGetTopRatedMovieBriefListCategory(
        movieBriefListRepository: MovieBriefListRepository
    ): GetTopRatedMovieBriefList = GetTopRatedMovieBriefList(movieBriefListRepository::getTopRatedMovieBriefList)

    @Provides
    @Singleton
    fun provideGetMovieDetailedApiUseCase(movieDetailedRepository: MovieDetailedRepository) =
        GetMovieDetailedApiUseCase(movieDetailedRepository::getMovieDetailedData)
}
