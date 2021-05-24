package com.lefarmico.remote_module

import com.lefarmico.remote_module.tmdbEntity.TmdbApi

interface RemoteProvider {
    fun provideRemote(): TmdbApi
}
