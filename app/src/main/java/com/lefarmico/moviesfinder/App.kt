package com.lefarmico.moviesfinder

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.lefarmico.moviesfinder.di.*
import com.lefarmico.moviesfinder.notifications.NotificationConstants.CHANNEL_ID
import com.lefarmico.remote_module.DaggerRemoteComponent

class App : Application() {

    private val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

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

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "WatchLaterChannel"
            val descriptionText = "FilmsSearch notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}
