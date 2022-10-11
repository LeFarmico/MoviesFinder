package com.lefarmico.moviesfinder.injection.module

import android.content.Context
import androidx.room.Room
import com.lefarmico.moviesfinder.data.dataBase.AppDatabase
import com.lefarmico.moviesfinder.data.dataBase.dao.SavedMoviesDao
import com.lefarmico.moviesfinder.data.http.request.TmdbApi
import com.lefarmico.moviesfinder.data.manager.MovieBriefListRepository
import com.lefarmico.moviesfinder.data.manager.MovieDetailedRepository
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
            "movie_db"
        ).build().itemDao()

    @Provides
    @Singleton
    fun provideMovieDetailedRepository(
        savedMoviesDao: SavedMoviesDao,
        tmdbApi: TmdbApi
    ): MovieDetailedRepository = MovieDetailedRepository(savedMoviesDao, tmdbApi)

    @Provides
    @Singleton
    fun provideMovieBriefListRepository(
        tmdbApi: TmdbApi,
        savedMoviesDao: SavedMoviesDao
    ): MovieBriefListRepository = MovieBriefListRepository(tmdbApi, savedMoviesDao)
}
