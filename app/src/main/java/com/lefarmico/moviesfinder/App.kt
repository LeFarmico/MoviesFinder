package com.lefarmico.moviesfinder

import android.app.Application
import android.util.Log
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.entity.TmdbApi
import com.lefarmico.moviesfinder.di.AppComponent
import com.lefarmico.moviesfinder.di.DaggerAppComponent
import com.lefarmico.moviesfinder.private.PrivateData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {

    lateinit var repo: MainRepository
    lateinit var interactor: Interactor
    lateinit var retrofitService: TmdbApi

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

        appComponent = DaggerAppComponent.create()
        repo = MainRepository()

        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                }
            )
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(PrivateData.ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofitService = retrofit.create(TmdbApi::class.java)
        interactor = Interactor(repo, retrofitService)
    }
}
