
package com.lefarmico.moviesfinder.di

import dagger.Component
import javax.inject.Singleton

@Component(modules = [PresentersModule::class])

@Singleton
interface AppComponent {

    // Activities

    // Fragments
}

/*
    Инжектим класс
    При создании appComponent - он будет искать инжект классы
    Потом пойдет в appComponents и будет искать соответствующий модуль
    Потом пройдет в него и вернет функцию отмеченную Provides возвращающую нужный объект

 */
