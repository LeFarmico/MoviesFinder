package com.lefarmico.moviesfinder.injection.module

import com.lefarmico.moviesfinder.ui.navigation.api.Router
import com.lefarmico.moviesfinder.ui.navigation.api.resolver.DialogResolver
import com.lefarmico.moviesfinder.ui.navigation.api.resolver.NotificationResolver
import com.lefarmico.moviesfinder.ui.navigation.api.resolver.ScreenResolver
import com.lefarmico.moviesfinder.ui.navigation.impl.DialogResolverImpl
import com.lefarmico.moviesfinder.ui.navigation.impl.NotificationResolverImpl
import com.lefarmico.moviesfinder.ui.navigation.impl.RouterImpl
import com.lefarmico.moviesfinder.ui.navigation.impl.ScreenResolverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    @Singleton
    abstract fun bindScreeResolver(screenResolverImpl: ScreenResolverImpl): ScreenResolver

    @Binds
    @Singleton
    abstract fun bindNotificationResolver(notificationResolverImpl: NotificationResolverImpl): NotificationResolver

    @Binds
    @Singleton
    abstract fun bindDialogResolver(dialogResolverImpl: DialogResolverImpl): DialogResolver

    @Binds
    @Singleton
    abstract fun bindRouter(routerImpl: RouterImpl): Router
}
