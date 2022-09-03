package com.lefarmico.moviesfinder.injection.module

import android.content.Context
import com.lefarmico.moviesfinder.data.manager.Interactor
import com.lefarmico.moviesfinder.data.manager.MainRepository
import com.lefarmico.moviesfinder.ui.common.provider.PreferenceProvider
import com.lefarmico.remote_module.tmdbEntity.TmdbApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi, preferenceProvider: PreferenceProvider) =
        Interactor(repo = repository, retrofitService = tmdbApi, preferenceProvider = preferenceProvider)

    @Provides
    @Singleton
    fun providePreferenceProvider() = PreferenceProvider(context)
}
