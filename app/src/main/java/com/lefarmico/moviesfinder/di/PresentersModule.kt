
package com.lefarmico.moviesfinder.di

import com.lefarmico.moviesfinder.presenters.MainActivityPresenter
import com.lefarmico.moviesfinder.presenters.MainActivityPresenterImpl
import com.lefarmico.moviesfinder.presenters.MovieFragmentPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentersModule {

    @Provides @Singleton
    fun createItemsPresenter(): MovieFragmentPresenter {
        return MovieFragmentPresenter()
    }

    @Provides @Singleton
    fun createMainActivityPresenter(): MainActivityPresenter {
        return MainActivityPresenterImpl()
    }
}
