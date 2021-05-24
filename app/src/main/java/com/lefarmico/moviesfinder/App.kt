package com.lefarmico.moviesfinder

import android.app.Application
import android.util.Log
import com.lefarmico.moviesfinder.di.*
import com.lefarmico.remote_module.DaggerRemoteComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var instance: App
            private set
    }
    override fun onCreate() {
        super.onCreate()

        instance = this

        val tag = this.javaClass.canonicalName
        Log.d(tag, "on Create")

        val remoteProvider = DaggerRemoteComponent.create()

        appComponent = DaggerAppComponent.builder()
            .remoteProvider(remoteProvider)
            .dataBaseModel(DataBaseModel())
            .domainModule(DomainModule(this))
            .build()
    }
}
