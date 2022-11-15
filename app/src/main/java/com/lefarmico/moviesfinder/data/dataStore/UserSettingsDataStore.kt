package com.lefarmico.moviesfinder.data.dataStore

import com.lefarmico.moviesfinder.data.dataStore.parameter.Language

interface UserSettingsDataStore {

    fun setLanguage(language: Language)

    fun getLanguage(): Language

    fun getLanguageAsString(): String
}
