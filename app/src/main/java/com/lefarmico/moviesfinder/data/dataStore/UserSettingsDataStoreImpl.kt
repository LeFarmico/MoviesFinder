package com.lefarmico.moviesfinder.data.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.lefarmico.moviesfinder.data.dataStore.parameter.Language
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class UserSettingsDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserSettingsDataStore {

    override suspend fun setLanguage(language: Language) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USERS_LANGUAGE] = language.name
        }
    }

    override suspend fun getLanguage(): Language = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USERS_LANGUAGE] ?: Language.DEFAULT.name
    }.map { languageParameter ->
        when (languageParameter) {
            "US" -> Language.ENGLISH
            "RU" -> Language.RUSSIAN
            "UC" -> Language.UKRAINIAN
            else -> {
                throw IllegalArgumentException("Illegal language string parameter")
            }
        }
    }.first()

    override suspend fun getLanguageAsString(): String = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USERS_LANGUAGE] ?: Language.DEFAULT.name
    }.first()
}
