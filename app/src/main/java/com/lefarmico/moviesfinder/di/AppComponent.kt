
package com.lefarmico.moviesfinder.di

import com.lefarmico.moviesfinder.viewModels.MainActivityViewModel
import com.lefarmico.moviesfinder.viewModels.MovieFragmentViewModel
import com.lefarmico.moviesfinder.viewModels.ProfileFragmentViewModel
import com.lefarmico.moviesfinder.viewModels.SearchFragmentViewModel
import com.lefarmico.remote_module.RemoteProvider
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [RemoteProvider::class],
    modules = [
        DataBaseModel::class,
        DomainModule::class
    ]
)

@Singleton
interface AppComponent {
    // ViewModels
    fun inject(viewModel: ProfileFragmentViewModel)
    fun inject(viewModel: MainActivityViewModel)
    fun inject(viewModel: MovieFragmentViewModel)
    fun inject(viewModel: SearchFragmentViewModel)
}

/*
    Инжектим класс
    При создании appComponent - он будет искать инжект классы
    Потом пойдет в appComponents и будет искать соответствующий модуль
    Потом пройдет в него и вернет функцию отмеченную Provides возвращающую нужный объект

 */
