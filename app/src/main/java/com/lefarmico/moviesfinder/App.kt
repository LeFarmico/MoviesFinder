package com.lefarmico.moviesfinder

import android.app.Application
import android.util.Log
import com.lefarmico.moviesfinder.di.AppComponent
import com.lefarmico.moviesfinder.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        val tag = this.javaClass.canonicalName
        Log.d(tag, "on Create")
        appComponent = DaggerAppComponent.create()
    }
}
