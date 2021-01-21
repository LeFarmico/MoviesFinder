
package com.lefarmico.moviesfinder.di

import com.lefarmico.moviesfinder.presenters.ItemsPresenter
import dagger.Module
import dagger.Provides

@Module
class PresentersModule {

    @Provides
    fun createItemsPresenter(): ItemsPresenter {
        return ItemsPresenter()
    }
}
