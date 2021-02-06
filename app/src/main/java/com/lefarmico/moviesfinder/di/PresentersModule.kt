
package com.lefarmico.moviesfinder.di

import com.lefarmico.moviesfinder.presenters.MoviePresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentersModule {

    @Provides @Singleton
    fun createItemsPresenter(): MoviePresenter {
        return MoviePresenter()
    }
}
