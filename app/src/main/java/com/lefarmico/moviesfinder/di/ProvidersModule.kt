package com.lefarmico.moviesfinder.di

import com.lefarmico.moviesfinder.presenters.MoviePresenter
import com.lefarmico.moviesfinder.providers.MovieItemsProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProvidersModule {

    @Provides @Singleton
    fun createMovieItemsProvider(presenter: MoviePresenter): MovieItemsProvider {
        return MovieItemsProvider(presenter = presenter)
    }
}
