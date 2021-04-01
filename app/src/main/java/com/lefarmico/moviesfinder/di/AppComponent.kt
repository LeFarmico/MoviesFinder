
package com.lefarmico.moviesfinder.di

import com.lefarmico.moviesfinder.activities.MainActivity
import com.lefarmico.moviesfinder.fragments.FavoritesFragment
import com.lefarmico.moviesfinder.fragments.MovieFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        PresentersModule::class,
        DataBaseModel::class,
        DomainModule::class,
        RemoteModule::class
    ]
)

@Singleton
interface AppComponent {

    // Activities
    fun inject(activity: MainActivity)
    // Fragments
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: MovieFragment)
}

/*
    Инжектим класс
    При создании appComponent - он будет искать инжект классы
    Потом пойдет в appComponents и будет искать соответствующий модуль
    Потом пройдет в него и вернет функцию отмеченную Provides возвращающую нужный объект

 */
