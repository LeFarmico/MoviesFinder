package com.lefarmico.moviesfinder

import android.app.Application
import android.util.Log
import com.lefarmico.moviesfinder.di.*

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

        appComponent = DaggerAppComponent.builder()
            .dataBaseModel(DataBaseModel())
            .domainModule(DomainModule(this))
            .remoteModule(RemoteModule())
            .build()
    }
}
