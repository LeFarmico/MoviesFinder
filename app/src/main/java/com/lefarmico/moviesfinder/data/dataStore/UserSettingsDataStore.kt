package com.lefarmico.moviesfinder.data.dataStore

import com.lefarmico.moviesfinder.data.dataStore.parameter.Language

interface UserSettingsDataStore {

    suspend fun setLanguage(language: Language)

    suspend fun getLanguage(): Language

    suspend fun getLanguageAsString(): String
}
