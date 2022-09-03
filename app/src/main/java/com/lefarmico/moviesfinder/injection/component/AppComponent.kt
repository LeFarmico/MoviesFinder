
package com.lefarmico.moviesfinder.injection.component

import com.lefarmico.moviesfinder.injection.module.DataBaseModule
import com.lefarmico.moviesfinder.injection.module.DomainModule
import com.lefarmico.moviesfinder.ui.home.HomeViewModel
import com.lefarmico.moviesfinder.ui.main.MainActivityViewModel
import com.lefarmico.moviesfinder.ui.profile.ProfileViewModel
import com.lefarmico.moviesfinder.ui.search.SearchViewModel
import com.lefarmico.remote_module.RemoteProvider
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [RemoteProvider::class],
    modules = [
        DataBaseModule::class,
        DomainModule::class
    ]
)

@Singleton
interface AppComponent {
    fun inject(viewModel: ProfileViewModel)
    fun inject(viewModel: MainActivityViewModel)
    fun inject(viewModel: HomeViewModel)
    fun inject(viewModel: SearchViewModel)
}
