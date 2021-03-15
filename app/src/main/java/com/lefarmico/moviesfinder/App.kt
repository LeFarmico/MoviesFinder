package com.lefarmico.moviesfinder

import android.app.Application
import android.util.Log
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.di.*

class App : Application() {

    lateinit var interactor: Interactor

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
            .presentersModule(PresentersModule())
            .remoteModule(RemoteModule())
            .build()
    }
}
