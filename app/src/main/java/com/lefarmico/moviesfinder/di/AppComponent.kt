
package com.lefarmico.moviesfinder.di

import com.lefarmico.moviesfinder.fragments.MovieFragment
import com.lefarmico.moviesfinder.viewModels.MainActivityViewModel
import com.lefarmico.moviesfinder.viewModels.MovieFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DataBaseModel::class,
        DomainModule::class,
        RemoteModule::class
    ]
)

@Singleton
interface AppComponent {
    fun inject(fragment: MovieFragment)
    // ViewModels
    fun inject(viewModel: MainActivityViewModel)
    fun inject(viewModel: MovieFragmentViewModel)
}

/*
    Инжектим класс
    При создании appComponent - он будет искать инжект классы
    Потом пойдет в appComponents и будет искать соответствующий модуль
    Потом пройдет в него и вернет функцию отмеченную Provides возвращающую нужный объект

 */
