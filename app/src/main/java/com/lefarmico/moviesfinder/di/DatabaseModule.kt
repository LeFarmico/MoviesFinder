
package com.lefarmico.moviesfinder.di

import com.lefarmico.moviesfinder.models.ItemsDatabaseModel
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun providesItemDatabase(): ItemsDatabaseModel {
        return ItemsDatabaseModel()
    }
}