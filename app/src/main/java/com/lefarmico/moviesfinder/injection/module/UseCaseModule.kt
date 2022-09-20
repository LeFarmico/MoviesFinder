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
    ): GetPopularMovieBriefListUseCase =
        GetPopularMovieBriefListUseCase(movieBriefListRepository::getPopularMovieBriefList)

    @Singleton
    @Provides
    fun provideGetUpcomingMovieBriefListCategory(
        movieBriefListRepository: MovieBriefListRepository
    ): GetUpcomingMovieBriefListUseCase = GetUpcomingMovieBriefListUseCase(movieBriefListRepository::getUpcomingMovieBriefList)

    @Singleton
    @Provides
    fun provideGetNowPlayingMovieBriefListCategory(
        movieBriefListRepository: MovieBriefListRepository
    ): GetNowPlayingMovieBriefListUseCase = GetNowPlayingMovieBriefListUseCase(movieBriefListRepository::getNowPlayingMovieBriefList)

    @Singleton
    @Provides
    fun provideGetTopRatedMovieBriefListCategory(
        movieBriefListRepository: MovieBriefListRepository
    ): GetTopRatedMovieBriefListUseCase = GetTopRatedMovieBriefListUseCase(movieBriefListRepository::getTopRatedMovieBriefList)

    @Provides
    @Singleton
    fun provideGetMovieDetailedApiUseCase(movieDetailedRepository: MovieDetailedRepository) =
        GetMovieDetailedApiUseCase(movieDetailedRepository::getMovieDetailedData)

    @Provides
    @Singleton
    fun provideGetRecommendationsMovieBriefListUseCase(
        movieBriefListRepository: MovieBriefListRepository
    ) = GetRecommendationsMovieBriefListUseCase(movieBriefListRepository::getRecommendationsMovieBriefList)
}
